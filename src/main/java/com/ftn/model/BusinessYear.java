package com.ftn.model;

import com.fasterxml.jackson.annotation.*;
import com.ftn.constants.Sql;
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
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "company")
@SQLDelete(sql = Sql.UPDATE + "business_year" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class BusinessYear extends BaseModel {

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private boolean closed;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("businessYears")
    @JsonManagedReference
    private Company company;

    @OneToMany(mappedBy = "businessYear", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "businessYear", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<WarehouseCard> warehouseCards = new ArrayList<>();

}
