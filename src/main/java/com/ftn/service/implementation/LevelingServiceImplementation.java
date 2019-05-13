package com.ftn.service.implementation;

import com.ftn.model.WarehouseCardAnalytics;
import com.ftn.model.dto.WarehouseCardAnalyticsDTO;
import com.ftn.model.dto.WarehouseCardDTO;
import com.ftn.service.LevelingService;
import com.ftn.service.WarehouseCardAnalyticsService;
import com.ftn.service.WarehouseCardService;
import org.springframework.stereotype.Service;

/**
 * Created by JELENA on 6.7.2017.
 */
@Service
public class LevelingServiceImplementation implements LevelingService {

    private final WarehouseCardService warehouseCardService;

    private final WarehouseCardAnalyticsService warehouseCardAnalyticsService;

    public LevelingServiceImplementation(WarehouseCardService warehouseCardService, WarehouseCardAnalyticsService warehouseCardAnalyticsService) {
        this.warehouseCardService = warehouseCardService;
        this.warehouseCardAnalyticsService = warehouseCardAnalyticsService;
    }

    @Override
    public WarehouseCardDTO level(WarehouseCardDTO warehouseCardDTO) {

        double averagePrice = warehouseCardDTO.getAveragePrice();
        double totalQuantity = warehouseCardDTO.getTotalQuantity();
        double totalValue = warehouseCardDTO.getTotalValue();

        if (averagePrice * totalQuantity != totalValue) {
            double leveling = totalQuantity*averagePrice - totalValue;

            WarehouseCardAnalyticsDTO warehouseCardAnalyticsDTO = new WarehouseCardAnalyticsDTO();
            warehouseCardAnalyticsDTO.setDirection(WarehouseCardAnalytics.Direction.INCOMING.toString());
            warehouseCardAnalyticsDTO.setTrafficType(WarehouseCardAnalytics.TrafficType.LEVELING.toString());
            warehouseCardAnalyticsDTO.setAveragePrice(averagePrice);
            warehouseCardAnalyticsDTO.setQuantity(0.0);
            warehouseCardAnalyticsDTO.setValue(leveling);
            warehouseCardAnalyticsDTO.setWarehouseCard(warehouseCardDTO);

            WarehouseCardAnalyticsDTO createdAnalyticsDTO = warehouseCardAnalyticsService.create(warehouseCardAnalyticsDTO);

            warehouseCardDTO.getWarehouseCardAnalytics().add(createdAnalyticsDTO);
            warehouseCardDTO.setTotalValue(totalValue + leveling);
            warehouseCardService.update(warehouseCardDTO.getId(), warehouseCardDTO);

        }

        return warehouseCardDTO;
    }
}
