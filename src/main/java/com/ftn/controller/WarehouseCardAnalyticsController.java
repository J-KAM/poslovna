package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.WarehouseCardAnalytics;
import com.ftn.model.dto.WarehouseCardAnalyticsDTO;
import com.ftn.service.WarehouseCardAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * Created by Jasmina on 31/05/2017.
 */
@RestController
@RequestMapping("/api/warehouseCardAnalytics")
public class WarehouseCardAnalyticsController {

    private final WarehouseCardAnalyticsService warehouseCardAnalyticsService;

    @Autowired
    public WarehouseCardAnalyticsController(WarehouseCardAnalyticsService warehouseCardAnalyticsService) {
        this.warehouseCardAnalyticsService = warehouseCardAnalyticsService;
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(warehouseCardAnalyticsService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping(value = "/warehouseCards/{warehouseCardId}")
    public ResponseEntity read(@PathVariable Long warehouseCardId) {
        return new ResponseEntity<>(warehouseCardAnalyticsService.read(warehouseCardId), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody WarehouseCardAnalytics warehouseCardAnalytics, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }

        return new ResponseEntity<>(warehouseCardAnalyticsService.create(warehouseCardAnalytics), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody WarehouseCardAnalyticsDTO warehouseCardAnalyticsDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }

        return new ResponseEntity<>(warehouseCardAnalyticsService.update(id, warehouseCardAnalyticsDTO), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        warehouseCardAnalyticsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
