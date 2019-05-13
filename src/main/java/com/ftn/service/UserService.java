package com.ftn.service;

import com.ftn.model.dto.RegisterUserDTO;
import com.ftn.model.dto.UserDTO;

import java.util.List;

/**
 * Created by Alex on 5/18/17.
 */
public interface UserService {

    UserDTO create(RegisterUserDTO registerUserDTO);

    UserDTO read();

    List<UserDTO> readEmployees();
}
