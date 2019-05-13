package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.dto.BaseDTO;
import com.ftn.model.dto.WarehouseCardDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "warehouse_card" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class WarehouseCard extends BaseModel {

    private double averagePrice;

    private double initialQuantity;

    private double initialValue;

    private double quantityEntryTraffic;

    private double valueEntryTraffic;

    private double quantityExitTraffic;

    private double valueExitTraffic;

    private double totalQuantity;

    private double totalValue;

    @ManyToOne(optional = false)
    private BusinessYear businessYear;

    @OneToMany(mappedBy = "warehouseCard", cascade = CascadeType.ALL)
    private List<WarehouseCardAnalytics> warehouseCardAnalytics = new ArrayList<>();

    @ManyToOne(optional = false)
    private Ware ware;

    @ManyToOne(optional = false)
    private Warehouse warehouse;

    public WarehouseCard(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public void merge(WarehouseCardDTO warehouseCardDTO) {
        this.averagePrice = warehouseCardDTO.getAveragePrice();
        this.initialQuantity = warehouseCardDTO.getInitialQuantity();
        this.initialValue = warehouseCardDTO.getInitialValue();
        this.quantityEntryTraffic = warehouseCardDTO.getQuantityEntryTraffic();
        this.valueEntryTraffic = warehouseCardDTO.getValueEntryTraffic();
        this.quantityExitTraffic = warehouseCardDTO.getQuantityExitTraffic();
        this.valueExitTraffic = warehouseCardDTO.getValueExitTraffic();
        this.totalQuantity = warehouseCardDTO.getTotalQuantity();
        this.totalValue = warehouseCardDTO.getTotalValue();
        this.businessYear = warehouseCardDTO.getBusinessYear().construct();
        this.ware = warehouseCardDTO.getWare().construct();
        this.warehouse = warehouseCardDTO.getWarehouse().construct();
    }

    public WarehouseCard constructNextYearCard() {
        final WarehouseCard newWarehouseCard = new WarehouseCard();
        newWarehouseCard.setAveragePrice(this.getAveragePrice());
        newWarehouseCard.setInitialQuantity(this.getTotalQuantity());
        newWarehouseCard.setInitialValue(this.getTotalValue());
        newWarehouseCard.setTotalQuantity(this.getTotalQuantity());
        newWarehouseCard.setTotalValue(this.getTotalValue());
        newWarehouseCard.setWare(this.getWare());
        newWarehouseCard.setWarehouse(this.getWarehouse());
        return newWarehouseCard;
    }
}
