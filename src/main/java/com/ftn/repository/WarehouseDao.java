package com.ftn.repository;

import com.ftn.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Olivera on 30.5.2017..
 */
public interface WarehouseDao extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findById(Long id);

    Optional<Warehouse> findByIdAndEmployeeId(Long id, Long employeeId);

    List<Warehouse> findByEmployeeId(Long id);

    List<Warehouse> findByCompanyId(Long id);
}
