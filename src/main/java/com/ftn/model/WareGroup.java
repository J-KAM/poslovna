package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.dto.BaseDTO;
import com.ftn.model.dto.WareGroupDTO;
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
@SQLDelete(sql = Sql.UPDATE + "ware_group" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class WareGroup extends BaseModel {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "wareGroup", cascade = CascadeType.ALL)
    private List<Ware> wares = new ArrayList<>();

    @ManyToOne(optional = false)
    private Company company;

    public WareGroup(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public void merge(WareGroupDTO wareGroupDTO) {
        this.name = wareGroupDTO.getName();
        this.company = wareGroupDTO.getCompany().construct();
    }
}
