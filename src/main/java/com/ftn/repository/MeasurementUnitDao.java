package com.ftn.repository;

import com.ftn.model.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Alex on 5/20/17.
 */
public interface MeasurementUnitDao extends JpaRepository<MeasurementUnit, Long> {

    Optional<MeasurementUnit> findById(Long id);
}
