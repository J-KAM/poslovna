package com.ftn.model;

import com.ftn.constants.Sql;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "warehouse_card" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class WarehouseCard extends BaseModel {

    @Column
    private double averagePrice;

    @Column
    private double initialQuantity;

    @Column
    private double initialValue;

    @Column
    private double quantityEntryTraffic;

    @Column
    private double valueEntryTraffic;

    @Column
    private double quantityExitTraffic;

    @Column
    private double valueExitTraffic;

    @Column
    private double totalQuantity;

    @Column
    private double totalValue;

    @ManyToOne(optional = false)
    private BusinessYear businessYear;

    @ManyToOne(optional = false)
    private Ware ware;

    @ManyToOne(optional = false)
    private Warehouse warehouse;

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
