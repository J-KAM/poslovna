package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.BusinessYearDTO;
import com.ftn.service.BusinessYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by JELENA on 30.5.2017.
 */
@RestController
@RequestMapping("/api/businessYears")
public class BusinessYearController {

    private final BusinessYearService businessYearService;

    @Autowired
    public BusinessYearController(BusinessYearService businessYearService) {
        this.businessYearService = businessYearService;
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(businessYearService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody BusinessYearDTO businessYearDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }

        return new ResponseEntity<>(businessYearService.create(businessYearDTO), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PatchMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody BusinessYearDTO businessYearDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(businessYearService.update(id, businessYearDTO), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        businessYearService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
