package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.ReportDataDTO;
import com.ftn.model.dto.WareDTO;
import com.ftn.model.dto.WarehouseCardDTO;
import com.ftn.service.LevelingService;
import com.ftn.service.WarehouseCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by JELENA on 31.5.2017.
 */
@RestController
@RequestMapping("/api/warehouseCards")
public class WarehouseCardController {

    private final WarehouseCardService warehouseCardService;
    private final LevelingService levelingService;

    public WarehouseCardController(WarehouseCardService warehouseCardService, LevelingService levelingService) {
        this.warehouseCardService = warehouseCardService;
        this.levelingService = levelingService;
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(warehouseCardService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @GetMapping(value = "/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return new ResponseEntity<>(warehouseCardService.read(id), HttpStatus.OK);
    }


    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody WarehouseCardDTO warehouseCardDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(warehouseCardService.create(warehouseCardDTO), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PatchMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody WarehouseCardDTO warehouseCardDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(warehouseCardService.update(id, warehouseCardDTO), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        warehouseCardService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping(value = "/generateReport", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity generateReport(@RequestBody ReportDataDTO reportDataDTO) {
        return new ResponseEntity<>(warehouseCardService.generateReport(reportDataDTO), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping(value="/level")
    public ResponseEntity level(@Valid @RequestBody WarehouseCardDTO warehouseCardDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(levelingService.level(warehouseCardDTO), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping(value="/card")
    public ResponseEntity getCardForWare(@RequestBody WareDTO wareDTO){
        return new ResponseEntity<>(warehouseCardService.getWarehouseCardForWare(wareDTO), HttpStatus.OK);
    }
}
