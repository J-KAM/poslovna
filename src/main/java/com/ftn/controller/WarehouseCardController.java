package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.Ware;
import com.ftn.model.WarehouseCard;
import com.ftn.model.ReportDataDTO;
import com.ftn.service.LevelingService;
import com.ftn.service.WarehouseCardService;
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
 * Created by JELENA on 31.5.2017.
 */
@RestController
@RequestMapping("/api/warehouseCards")
public class WarehouseCardController {

    private final WarehouseCardService warehouseCardService;
    private final LevelingService levelingService;

    @Autowired
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
    @PostMapping(value = "/generateReport", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity generateReport(@RequestBody ReportDataDTO reportDataDTO) {
        return new ResponseEntity<>(warehouseCardService.generateReport(reportDataDTO), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping(value="/level")
    public ResponseEntity level(@Valid @RequestBody WarehouseCard warehouseCard, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(levelingService.level(warehouseCard), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping(value="/card")
    public ResponseEntity getCardForWare(@RequestBody Ware ware){
        return new ResponseEntity<>(warehouseCardService.getWarehouseCardForWare(ware), HttpStatus.OK);
    }
}
