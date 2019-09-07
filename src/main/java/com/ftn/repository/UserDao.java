package com.ftn.repository;

import com.ftn.model.User;
import com.ftn.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Alex on 10/28/16.
 */
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);

}
