package com.ftn.service.implementation;

import com.ftn.model.User;
import com.ftn.repository.UserDao;
import com.ftn.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public <E extends  User> E getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDao.findByUsername(authentication.getName());
    }
}
