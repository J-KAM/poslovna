package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.MeasurementUnit;
import com.ftn.service.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by Alex on 5/20/17.
 */
@RestController
@RequestMapping("/api/measurementUnits")
public class MeasurementUnitController {

    private final MeasurementUnitService measurementUnitService;

    @Autowired
    public MeasurementUnitController(MeasurementUnitService measurementUnitService) {
        this.measurementUnitService = measurementUnitService;
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(measurementUnitService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping(value = "/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return new ResponseEntity<>(measurementUnitService.read(id), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.ADMIN)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody MeasurementUnit measurementUnit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(measurementUnitService.create(measurementUnit), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.ADMIN)
    @PatchMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody MeasurementUnit measurementUnit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(measurementUnitService.update(id, measurementUnit), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.ADMIN)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        measurementUnitService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
