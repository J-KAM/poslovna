package com.ftn.service.implementation;

import com.ftn.model.Employee;
import com.ftn.model.User;
import com.ftn.model.dto.RegisterUserDTO;
import com.ftn.model.dto.UserDTO;
import com.ftn.repository.UserDao;
import com.ftn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 5/18/17.
 */
@Service
public class UserServiceImplementation implements UserService {

    private final BCryptPasswordEncoder encoder;

    private final UserDao userDao;

    @Autowired
    public UserServiceImplementation(BCryptPasswordEncoder encoder, UserDao userDao) {
        this.encoder = encoder;
        this.userDao = userDao;
    }

    @Override
    public UserDTO create(RegisterUserDTO registerUserDTO) {
        final Employee employee = registerUserDTO.construct();
        employee.setRole(User.Role.EMPLOYEE);
        // TODO: Uncomment this when development ends
        //employee.setPassword(encoder.encode(employee.getPassword()));
        employee.setEnabled(true);
        userDao.save(employee);
        return new UserDTO(employee);
    }

    @Override
    public UserDTO read() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userDao.findByUsername(authentication.getName());
        return new UserDTO(user);
    }

    @Override
    public List<UserDTO> readEmployees() {
        final List<User> users = userDao.findByRole(User.Role.EMPLOYEE);
        return users.stream().map(UserDTO::new).collect(Collectors.toList());


    }
}
