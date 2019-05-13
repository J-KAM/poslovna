package com.ftn.model.dto;

import com.ftn.model.MeasurementUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 5/20/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MeasurementUnitDTO extends BaseDTO {

    @NotNull
    private String name;

    @NotNull
    private String label;

    private List<WareDTO> wares = new ArrayList<>();

    public MeasurementUnitDTO(MeasurementUnit measurementUnit) {
        this(measurementUnit, true);
    }

    public MeasurementUnitDTO(MeasurementUnit measurementUnit, boolean cascade) {
        super(measurementUnit);
        this.name = measurementUnit.getName();
        this.label = measurementUnit.getLabel();
        if (cascade) {
            this.wares = measurementUnit.getWares().stream().map(ware -> new WareDTO(ware, false)).collect(Collectors.toList());
        }
    }

    public MeasurementUnit construct() {
        final MeasurementUnit measurementUnit = new MeasurementUnit(this);
        measurementUnit.setName(name);
        measurementUnit.setLabel(label);
        if (wares != null) {
            wares.forEach(wareDTO -> measurementUnit.getWares().add(wareDTO.construct()));
        }
        return measurementUnit;
    }
}
