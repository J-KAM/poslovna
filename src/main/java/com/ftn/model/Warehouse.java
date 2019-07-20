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
@EqualsAndHashCode(callSuper = true, exclude = {"employee", "company"})
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "warehouse" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class Warehouse extends BaseModel {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("warehouses")
    @JsonManagedReference
    private Employee employee;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("warehouses")
    @JsonManagedReference
    private Company company;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<WarehouseCard> warehouseCards = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Document> innerDocuments = new ArrayList<>();

}
