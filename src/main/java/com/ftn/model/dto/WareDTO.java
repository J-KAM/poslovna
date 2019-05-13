package com.ftn.model.dto;

import com.ftn.model.Ware;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 30.5.2017.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class WareDTO extends BaseDTO {

    @NotNull
    private String name;

    @NotNull
    private double packing;

    private List<DocumentUnitDTO> documentUnits = new ArrayList<>();

    private List<WarehouseCardDTO> warehouseCards = new ArrayList<>();

    @NotNull
    private MeasurementUnitDTO measurementUnit;

    @NotNull
    private WareGroupDTO wareGroup;

    public WareDTO(Ware ware) {
        this(ware, true);
    }

    public WareDTO(Ware ware, boolean cascade) {
        super(ware);
        this.name = ware.getName();
        this.packing = ware.getPacking();
        if (cascade) {
            this.documentUnits = ware.getDocumentUnits().stream().map(documentUnit -> new DocumentUnitDTO(documentUnit, false)).collect(Collectors.toList());
            this.measurementUnit = new MeasurementUnitDTO(ware.getMeasurementUnit(), false);
            this.wareGroup = new WareGroupDTO(ware.getWareGroup(), false);
            this.warehouseCards = ware.getWarehouseCards().stream().map(warehouseCard -> new WarehouseCardDTO(warehouseCard, false)).collect(Collectors.toList());
        }
    }

    public Ware construct() {
        final Ware ware = new Ware(this);
        ware.setName(name);
        ware.setPacking(packing);
        ware.setMeasurementUnit(measurementUnit != null ? measurementUnit.construct() : null);
        ware.setWareGroup(wareGroup != null ? wareGroup.construct() : null);
        if (documentUnits != null) {
            documentUnits.forEach(documentUnitDTO -> ware.getDocumentUnits().add(documentUnitDTO.construct()));
        }
        if (warehouseCards != null) {
            warehouseCards.forEach(warehouseCardDTO -> ware.getWarehouseCards().add(warehouseCardDTO.construct()));
        }
        return ware;
    }

}
