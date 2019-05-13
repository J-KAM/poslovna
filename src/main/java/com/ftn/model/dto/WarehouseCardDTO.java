package com.ftn.model.dto;

import com.ftn.model.WarehouseCard;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 31.5.2017.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class WarehouseCardDTO extends BaseDTO {

    private double averagePrice;

    private double initialQuantity;

    private double initialValue;

    private double quantityEntryTraffic;

    private double valueEntryTraffic;

    private double quantityExitTraffic;

    private double valueExitTraffic;

    private double totalQuantity;

    private double totalValue;

    @NotNull
    private BusinessYearDTO businessYear;

    private List<WarehouseCardAnalyticsDTO> warehouseCardAnalytics;

    @NotNull
    private WareDTO ware;

    @NotNull
    private WarehouseDTO warehouse;

    public WarehouseCardDTO(WarehouseCard warehouseCard) {
        this(warehouseCard, true);
    }

    public WarehouseCardDTO(WarehouseCard warehouseCard, boolean cascade) {
        super(warehouseCard);
        this.averagePrice = warehouseCard.getAveragePrice();
        this.initialQuantity = warehouseCard.getInitialQuantity();
        this.initialValue = warehouseCard.getInitialValue();
        this.quantityEntryTraffic = warehouseCard.getQuantityEntryTraffic();
        this.valueEntryTraffic = warehouseCard.getValueEntryTraffic();
        this.quantityExitTraffic = warehouseCard.getQuantityExitTraffic();
        this.valueExitTraffic = warehouseCard.getValueExitTraffic();
        this.totalQuantity = warehouseCard.getTotalQuantity();
        this.totalValue = warehouseCard.getTotalValue();
        if (cascade) {
            this.businessYear = new BusinessYearDTO(warehouseCard.getBusinessYear(), false);
            this.ware = new WareDTO(warehouseCard.getWare(), false);
            this.warehouse = new WarehouseDTO(warehouseCard.getWarehouse(), false);
            this.warehouseCardAnalytics = warehouseCard.getWarehouseCardAnalytics().stream().map(warehouseCardAnalytic -> new WarehouseCardAnalyticsDTO(warehouseCardAnalytic, false)).collect(Collectors.toList());
        }
    }

    public WarehouseCard construct() {
        final WarehouseCard warehouseCard = new WarehouseCard(this);

        warehouseCard.setAveragePrice(averagePrice);
        warehouseCard.setInitialQuantity(initialQuantity);
        warehouseCard.setInitialValue(initialValue);
        warehouseCard.setQuantityEntryTraffic(quantityEntryTraffic);
        warehouseCard.setValueEntryTraffic(valueEntryTraffic);
        warehouseCard.setQuantityExitTraffic(quantityExitTraffic);
        warehouseCard.setValueExitTraffic(valueExitTraffic);
        warehouseCard.setTotalQuantity(totalQuantity);
        warehouseCard.setTotalValue(totalValue);
        warehouseCard.setBusinessYear(businessYear != null ? businessYear.construct() : null);
        warehouseCard.setWare(ware != null ? ware.construct() : null);
        warehouseCard.setWarehouse(warehouse != null ? warehouse.construct() : null);
        if (warehouseCardAnalytics != null) {
            warehouseCardAnalytics.forEach(warehouseCardAnalyticsDTO -> warehouseCard.getWarehouseCardAnalytics().add(warehouseCardAnalyticsDTO.construct()));
        }

        return warehouseCard;
    }

}
