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
@EqualsAndHashCode(callSuper = true, exclude = "company")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "ware_group" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class WareGroup extends BaseModel {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "wareGroup", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Ware> wares = new ArrayList<>();

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("wareGroups")
    @JsonManagedReference
    private Company company;
}
