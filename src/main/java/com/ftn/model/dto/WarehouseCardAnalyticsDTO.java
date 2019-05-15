package com.ftn.model.dto;


import com.ftn.model.WarehouseCardAnalytics;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by Jasmina on 31/05/2017.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class WarehouseCardAnalyticsDTO extends BaseDTO {

    private String trafficType;

    private String direction;

    private double quantity;

    private double value;

    private double averagePrice;

    public WarehouseCardAnalyticsDTO(WarehouseCardAnalytics warehouseCardAnalytics) {
        this(warehouseCardAnalytics, false);
    }

    public WarehouseCardAnalyticsDTO(WarehouseCardAnalytics warehouseCardAnalytics, boolean cascade) {
        super(warehouseCardAnalytics);
        this.trafficType = warehouseCardAnalytics.getTrafficType().toString();
        this.direction = warehouseCardAnalytics.getDirection().toString();
        this.quantity = warehouseCardAnalytics.getQuantity();
        this.value = warehouseCardAnalytics.getValue();
        this.averagePrice = warehouseCardAnalytics.getAveragePrice();
    }

    public WarehouseCardAnalytics construct() {
        final WarehouseCardAnalytics warehouseCardAnalytics = new WarehouseCardAnalytics(this);
        warehouseCardAnalytics.setTrafficType(WarehouseCardAnalytics.TrafficType.valueOf(trafficType));
        warehouseCardAnalytics.setDirection(WarehouseCardAnalytics.Direction.valueOf(direction));
        warehouseCardAnalytics.setQuantity(quantity);
        warehouseCardAnalytics.setValue(value);
        warehouseCardAnalytics.setAveragePrice(averagePrice);
        return warehouseCardAnalytics;
    }
}
