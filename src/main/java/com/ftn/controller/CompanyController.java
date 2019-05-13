package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.CompanyDTO;
import com.ftn.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by Jasmina on 21/05/2017.
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(companyService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping(value = "/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.read(id), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.ADMIN)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody CompanyDTO companyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(companyService.create(companyDTO), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.ADMIN)
    @PatchMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody CompanyDTO companyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(companyService.update(id, companyDTO), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.ADMIN)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        companyService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
