package com.ftn.service.implementation;

import com.ftn.model.User;
import com.ftn.repository.UserDao;
import com.ftn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public User create(User user) {
        user.setRole(User.Role.EMPLOYEE);
        // TODO: Uncomment this when development ends
        //employee.setPassword(encoder.encode(employee.getPassword()));
        user.setEnabled(true);
        return userDao.save(user);
    }

    @Override
    public User read() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userDao.findByUsername(authentication.getName());
        return user;
    }

    @Override
    public List<User> readEmployees() {
        return userDao.findByRole(User.Role.EMPLOYEE);
    }
}
