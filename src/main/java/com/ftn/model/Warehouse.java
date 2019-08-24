package com.ftn.model;

import com.ftn.constants.Sql;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "warehouse" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class Warehouse extends BaseModel {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private User employee;

    @ManyToOne(optional = false)
    private Company company;
}
