package com.ftn.service;

import com.ftn.model.WarehouseCardAnalytics;
import com.ftn.model.dto.WarehouseCardAnalyticsDTO;

import java.util.List;

/**
 * Created by Olivera on 31.5.2017..
 */
public interface WarehouseCardAnalyticsService {

    List<WarehouseCardAnalyticsDTO> read();

    List<WarehouseCardAnalyticsDTO> read(Long warehouseCard);

    List<WarehouseCardAnalyticsDTO> read(Long id, WarehouseCardAnalytics.TrafficType trafficType);

    WarehouseCardAnalytics create(WarehouseCardAnalytics warehouseCardAnalytics);

    WarehouseCardAnalyticsDTO update(Long id, WarehouseCardAnalyticsDTO warehouseCardAnalyticsDTO);

    void delete(Long id);
}
