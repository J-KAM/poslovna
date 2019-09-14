package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.Warehouse;
import com.ftn.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by Olivera on 30.5.2017..
 */
@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(warehouseService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping(value = "/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return new ResponseEntity<>(warehouseService.read(id), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping(value = "/company/{id}")
    public ResponseEntity readByCompanyId(@PathVariable Long id) {
        return new ResponseEntity<>(warehouseService.readByCompany(id), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Warehouse warehouse, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(warehouseService.create(warehouse), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @PatchMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody Warehouse warehouse, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(warehouseService.update(id, warehouse), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        warehouseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping(value = "/generateReport", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity generateReport(@Valid @RequestBody Warehouse warehouse, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(warehouseService.generateReport(warehouse), HttpStatus.OK);
    }


}
