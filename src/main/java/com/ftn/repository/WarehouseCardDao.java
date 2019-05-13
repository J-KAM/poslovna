package com.ftn.repository;

import com.ftn.model.WarehouseCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by JELENA on 31.5.2017.
 */
public interface WarehouseCardDao extends JpaRepository<WarehouseCard, Long> {

    Optional<WarehouseCard> findById(Long id);

    WarehouseCard findByWareIdAndBusinessYearIdAndWarehouseId(Long wareId, Long yearId, Long warehouseId);

    List<WarehouseCard> findByWarehouseId(Long warehouseId);

    WarehouseCard findByWareIdAndBusinessYearId(Long wareId, Long yearId);

    List<WarehouseCard> findByBusinessYearIdAndWarehouseEmployeeId(Long yearId, Long employeeId);
}
