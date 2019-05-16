package com.ftn.service.implementation;

import com.ftn.model.WarehouseCard;
import com.ftn.model.WarehouseCardAnalytics;
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
    public WarehouseCard level(WarehouseCard warehouseCard) {

        double averagePrice = warehouseCard.getAveragePrice();
        double totalQuantity = warehouseCard.getTotalQuantity();
        double totalValue = warehouseCard.getTotalValue();

        if (averagePrice * totalQuantity != totalValue) {
            double leveling = totalQuantity*averagePrice - totalValue;

            WarehouseCardAnalytics warehouseCardAnalytics = new WarehouseCardAnalytics();
            warehouseCardAnalytics.setDirection(WarehouseCardAnalytics.Direction.INCOMING);
            warehouseCardAnalytics.setTrafficType(WarehouseCardAnalytics.TrafficType.LEVELING);
            warehouseCardAnalytics.setAveragePrice(averagePrice);
            warehouseCardAnalytics.setQuantity(0.0);
            warehouseCardAnalytics.setValue(leveling);
            warehouseCardAnalytics.setWarehouseCard(warehouseCard);

            WarehouseCardAnalytics createdAnalytics = warehouseCardAnalyticsService.create(warehouseCardAnalytics);

            warehouseCard.getWarehouseCardAnalytics().add(createdAnalytics);
            warehouseCard.setTotalValue(totalValue + leveling);
            warehouseCardService.update(warehouseCard.getId(), warehouseCard);

        }

        return warehouseCard;
    }
}
