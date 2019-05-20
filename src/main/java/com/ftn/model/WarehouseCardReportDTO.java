package com.ftn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Olivera on 6.7.2017..
 */
@Data
@NoArgsConstructor
public class WarehouseCardReportDTO {

    private Date date;

    private String trafficType;

    private String direction;

    private double entryQuantity;

    private double exitQuantity;

    private double totalQuantity;

    private double price;

    private double entryValue;

    private double exitValue;

    private double totalValue;


    public WarehouseCardReportDTO(WarehouseCardAnalytics warehouseCardAnalytics) {
        this.date = warehouseCardAnalytics.getCreated();
        String wcaTrafficType = warehouseCardAnalytics.getTrafficType().toString();
        if(wcaTrafficType.equals("RECEIPT")) {
            this.trafficType = "PR";
        } else if(wcaTrafficType.equals("DISPATCH")) {
            this.trafficType = "OT";
        } else if(wcaTrafficType.equals("INTER_WAREHOUSE_TRAFFIC")) {
            this.trafficType = "MM";
        } else if(wcaTrafficType.equals("LEVELING")) {
            this.trafficType = "NI";
        } else if(wcaTrafficType.equals("INITIAL_STATE")) {
            this.trafficType = "PS";
        } else if(wcaTrafficType.equals("CORRECTION")) {
            this.trafficType = "KOR";
        }
        if(warehouseCardAnalytics.getDirection().toString().equals("INCOMING")) {
            this.direction = "ULAZ";
            this.entryQuantity = warehouseCardAnalytics.getQuantity();
            this.entryValue = warehouseCardAnalytics.getValue();
        } else if(warehouseCardAnalytics.getDirection().toString().equals("OUTGOING")) {
            this.direction = "IZLAZ";
            this.exitQuantity = warehouseCardAnalytics.getQuantity();
            this.exitValue = warehouseCardAnalytics.getValue();
        }
        this.price = warehouseCardAnalytics.getAveragePrice();
        this.totalQuantity = warehouseCardAnalytics.getWarehouseCard().getTotalQuantity();
        this.totalValue = warehouseCardAnalytics.getWarehouseCard().getTotalValue();
    }
}
