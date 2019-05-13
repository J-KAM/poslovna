package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.DocumentDTO;
import com.ftn.service.BookingService;
import com.ftn.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by Olivera on 31.5.2017..
 */
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    private final BookingService bookingService;

    @Autowired
    public DocumentController(DocumentService documentService, BookingService bookingService) {
        this.documentService = documentService;
        this.bookingService = bookingService;
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(documentService.read(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @GetMapping(value = "/warehouse/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return new ResponseEntity<>(documentService.readByWarehouse(id), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody DocumentDTO documentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(documentService.create(documentDTO), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @PatchMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody DocumentDTO documentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(documentService.update(id, documentDTO), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize(Auth.EMPLOYEE)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        documentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Transactional
    @PostMapping(value="/book/{id}")
    public ResponseEntity book(@PathVariable Long id, @Valid @RequestBody DocumentDTO documentDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(bookingService.book(id, documentDTO), HttpStatus.OK);
    }
}
