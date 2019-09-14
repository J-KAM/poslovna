package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.service.BusinessYearService;
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
    @GetMapping(value = "/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return new ResponseEntity<>(businessYearService.read(id), HttpStatus.OK);
    }

}
