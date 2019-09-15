package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.service.WarehouseCardAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;


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
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(warehouseCardAnalyticsService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping(value = "/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return new ResponseEntity<>(warehouseCardAnalyticsService.read(id), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping(value = "/warehouseCards/{warehouseCardId}")
    public ResponseEntity readByWarehouseCardId(@PathVariable Long warehouseCardId) {
        return new ResponseEntity<>(warehouseCardAnalyticsService.readByWarehouseCardId(warehouseCardId), HttpStatus.OK);
    }

}
