package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.WareGroupDTO;
import com.ftn.service.WareGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by Jasmina on 22/05/2017.
 */
@RestController
@RequestMapping("/api/wareGroups")
public class WareGroupController {

    private final WareGroupService wareGroupService;

    @Autowired
    public WareGroupController(WareGroupService wareGroupService) {
        this.wareGroupService = wareGroupService;
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(wareGroupService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody WareGroupDTO wareGroupDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(wareGroupService.create(wareGroupDTO), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody WareGroupDTO wareGroupDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(wareGroupService.update(id, wareGroupDTO), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        wareGroupService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
