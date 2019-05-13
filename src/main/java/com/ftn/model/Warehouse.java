package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.dto.BaseDTO;
import com.ftn.model.dto.WarehouseDTO;
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
@SQLDelete(sql = Sql.UPDATE + "warehouse" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class Warehouse extends BaseModel {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Employee employee;

    @ManyToOne(optional = false)
    private Company company;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<WarehouseCard> warehouseCards = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Document> innerDocuments = new ArrayList<>();


    public Warehouse(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public void merge(WarehouseDTO warehouseDTO) {
        this.name = warehouseDTO.getName();

        this.company = warehouseDTO.getCompany().construct();
        if (warehouseDTO.getEmployee() != null) {
            this.employee = warehouseDTO.getEmployee().construct();
        }
    }
}
