package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.dto.BaseDTO;
import com.ftn.model.dto.MeasurementUnitDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "measurement_unit" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class MeasurementUnit extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String label;

    @OneToMany(mappedBy = "measurementUnit", cascade = CascadeType.ALL)
    private List<Ware> wares = new ArrayList<>();

    public MeasurementUnit(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public void merge(MeasurementUnitDTO measurementUnitDTO) {
        this.name = measurementUnitDTO.getName();
        this.label = measurementUnitDTO.getLabel();
    }
}
