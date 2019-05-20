package com.ftn.service;

import com.ftn.model.User;

import java.util.List;

/**
 * Created by Alex on 5/18/17.
 */
public interface UserService {

    User create(User user);

    User read();

    List<User> readEmployees();
}
