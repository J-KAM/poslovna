package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.dto.BaseDTO;
import com.ftn.model.dto.WareDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "ware" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class Ware extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double packing;

    @OneToMany(mappedBy = "ware", cascade = CascadeType.ALL)
    private List<DocumentUnit> documentUnits = new ArrayList<>();

    @OneToMany(mappedBy = "ware", cascade = CascadeType.ALL)
    private List<WarehouseCard> warehouseCards = new ArrayList<>();

    @ManyToOne(optional = false)
    private MeasurementUnit measurementUnit;

    @ManyToOne(optional = false)
    private WareGroup wareGroup;

    public Ware(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public void merge(WareDTO wareDTO) {
        this.name = wareDTO.getName();
        this.packing = wareDTO.getPacking();
        if (wareDTO.getMeasurementUnit() != null) {
            this.measurementUnit = wareDTO.getMeasurementUnit().construct();
        }
        if (wareDTO.getWareGroup() != null) {
            this.wareGroup = wareDTO.getWareGroup().construct();
        }
    }
}
