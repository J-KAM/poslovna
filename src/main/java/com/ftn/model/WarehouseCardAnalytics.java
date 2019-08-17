package com.ftn.model;

import com.ftn.constants.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "warehouse_card_analytics" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class WarehouseCardAnalytics extends BaseModel {

    public enum TrafficType {
        RECEIPT,
        DISPATCH,
        INTER_WAREHOUSE_TRAFFIC,
        LEVELING,
        INITIAL_STATE,
        CORRECTION
    }

    public enum Direction {
        INCOMING,
        OUTGOING
    }

    @Enumerated(EnumType.STRING)
    private TrafficType trafficType;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    private double quantity;

    private double value;

    private double averagePrice;

    @ManyToOne(optional = false)
    private WarehouseCard warehouseCard;

}
