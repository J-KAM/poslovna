package com.ftn.model;

import com.fasterxml.jackson.annotation.*;
import com.ftn.constants.Sql;
import lombok.AllArgsConstructor;
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
@EqualsAndHashCode(callSuper = true, exclude = {"measurementUnit", "wareGroup"})
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "ware" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class Ware extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double packing;

    @OneToMany(mappedBy = "ware", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<DocumentUnit> documentUnits = new ArrayList<>();

    @OneToMany(mappedBy = "ware", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<WarehouseCard> warehouseCards = new ArrayList<>();

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("wares")
    @JsonManagedReference
    private MeasurementUnit measurementUnit;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("wares")
    @JsonManagedReference
    private WareGroup wareGroup;

}
