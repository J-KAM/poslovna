package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.WareDTO;
import com.ftn.service.WareService;
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
@RequestMapping("/api/wares")
public class WareController {

    private final WareService wareService;

    @Autowired
    public WareController(WareService wareService) {
        this.wareService = wareService;
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(wareService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody WareDTO wareDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(wareService.create(wareDTO), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PatchMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody WareDTO wareDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(wareService.update(id, wareDTO), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        wareService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
