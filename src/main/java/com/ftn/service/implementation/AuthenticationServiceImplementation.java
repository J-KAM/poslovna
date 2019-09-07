package com.ftn.service.implementation;

import com.ftn.exception.NotFoundException;
import com.ftn.model.User;
import com.ftn.repository.UserDao;
import com.ftn.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Alex on 6/8/17.
 */
@Service
public class AuthenticationServiceImplementation implements AuthenticationService {

    private final UserDao userDao;

    @Autowired
    public AuthenticationServiceImplementation(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userDao.findByUsername(authentication.getName());
        if (!user.isPresent()) throw new NotFoundException("Current user can't be found!");
        return user.get();
    }
}
