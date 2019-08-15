package com.ftn.model;

import com.ftn.constants.Sql;
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
@SQLDelete(sql = Sql.UPDATE + "company" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class Company extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 9, unique = true)
    private long pib;

    private String address;

    @ManyToOne
    private Location location;

}
