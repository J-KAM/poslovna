package com.ftn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Olivera on 5.7.2017..
 */
@Data
@NoArgsConstructor
public class LagerListElementDTO {

    private Long wareId;

    private double packing;

    private String measurementUnit;

    private String wareName;

    private double quantity;

    private double calcPrice;

    private double calcPriceValue;

    public LagerListElementDTO(WarehouseCard warehouseCard) {
        this.wareId = warehouseCard.getWare().getId();
        this.packing = warehouseCard.getWare().getPacking();
        this.measurementUnit = warehouseCard.getWare().getMeasurementUnit().getName();
        this.wareName = warehouseCard.getWare().getName();
        this.quantity = warehouseCard.getTotalQuantity();
        this.calcPrice = warehouseCard.getAveragePrice();
        this.calcPriceValue = this.quantity * this.calcPrice;
    }

}
