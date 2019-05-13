package com.ftn.service;

import com.ftn.model.User;

/**
 * Created by Alex on 6/8/17.
 */
public interface AuthenticationService {

    <E extends  User> E getCurrentUser();
}
