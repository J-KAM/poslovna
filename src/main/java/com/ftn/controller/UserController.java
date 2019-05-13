package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.exception.BadRequestException;
import com.ftn.model.dto.RegisterUserDTO;
import com.ftn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by Alex on 10/28/16.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping(value = "/me")
    public ResponseEntity read() {
        return new ResponseEntity<>(userService.read(), HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/employees")
    public ResponseEntity create(@Valid @RequestBody RegisterUserDTO registerUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        return new ResponseEntity<>(userService.create(registerUserDTO), HttpStatus.CREATED);
    }

    @Transactional
    @PreAuthorize(Auth.AUTHENTICATED)
    @GetMapping(value = "/employees")
    public ResponseEntity readEmployees() {
        return new ResponseEntity<>(userService.readEmployees(), HttpStatus.OK);
    }
}
