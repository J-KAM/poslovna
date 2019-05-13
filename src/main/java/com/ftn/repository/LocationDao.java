package com.ftn.repository;

import com.ftn.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Jasmina on 21/05/2017.
 */
public interface LocationDao extends JpaRepository<Location, Long> {

    Optional<Location> findById(Long id);
}
