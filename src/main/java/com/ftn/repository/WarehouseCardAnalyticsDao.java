package com.ftn.repository;

import com.ftn.model.WarehouseCardAnalytics;
import com.ftn.model.enums.TrafficType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Olivera on 31.5.2017..
 */
public interface WarehouseCardAnalyticsDao extends JpaRepository<WarehouseCardAnalytics, Long> {

    Optional<WarehouseCardAnalytics> findById(Long id);

    List<WarehouseCardAnalytics> findByWarehouseCardId(Long warehouseCardId);

    List<WarehouseCardAnalytics> findByWarehouseCardIdAndTrafficTypeOrderByCreatedDesc(Long id, TrafficType trafficType);
}

