package com.ftn.service;

import com.ftn.model.WarehouseCardAnalytics;
import com.ftn.model.enums.TrafficType;

import java.util.List;

/**
 * Created by Olivera on 31.5.2017..
 */
public interface WarehouseCardAnalyticsService {

    List<WarehouseCardAnalytics> read();

    List<WarehouseCardAnalytics> read(Long warehouseCard);

    List<WarehouseCardAnalytics> read(Long id, TrafficType trafficType);

    WarehouseCardAnalytics create(WarehouseCardAnalytics warehouseCardAnalytics);

    WarehouseCardAnalytics update(Long id, WarehouseCardAnalytics warehouseCardAnalyticsDTO);

    void delete(Long id);
}
