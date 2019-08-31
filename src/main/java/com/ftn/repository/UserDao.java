package com.ftn.repository;

import com.ftn.model.User;
import com.ftn.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Alex on 10/28/16.
 */
public interface UserDao extends JpaRepository<User, Long> {

    <E extends User> E findByUsername(String username);

    <E extends User> List<E> findByRole(Role role);

}
