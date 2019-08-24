package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.enums.Direction;
import com.ftn.model.enums.TrafficType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column
    private TrafficType trafficType;

    @Enumerated(EnumType.STRING)
    @Column
    private Direction direction;

    @Column
    private double quantity;

    @Column
    private double value;

    @Column
    private double averagePrice;

    @ManyToOne(optional = false)
    private WarehouseCard warehouseCard;

}
