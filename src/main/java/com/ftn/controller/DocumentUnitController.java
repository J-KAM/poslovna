package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.DocumentUnit;
import com.ftn.service.DocumentUnitService;
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
@RequestMapping("/api/documentUnits")
public class DocumentUnitController {

    private final DocumentUnitService documentUnitService;

    @Autowired
    public DocumentUnitController(DocumentUnitService documentUnitService) {
        this.documentUnitService = documentUnitService;
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(documentUnitService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping(value = "/documents/{documentId}")
    public ResponseEntity read(@PathVariable Long documentId) {
        return new ResponseEntity<>(documentUnitService.read(documentId), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody DocumentUnit documentUnit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(documentUnitService.create(documentUnit), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PatchMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody DocumentUnit documentUnit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(documentUnitService.update(id, documentUnit), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        documentUnitService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
