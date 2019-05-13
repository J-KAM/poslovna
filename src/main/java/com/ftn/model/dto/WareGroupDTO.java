package com.ftn.model.dto;

import com.ftn.model.WareGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jasmina on 22/05/2017.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class WareGroupDTO extends BaseDTO {

    @NotNull
    private String name;

    private List<WareDTO> wares = new ArrayList<>();

    @NotNull
    private CompanyDTO company;

    public WareGroupDTO(WareGroup wareGroup) {
        this(wareGroup, true);
    }

    public WareGroupDTO(WareGroup wareGroup, boolean cascade) {
        super(wareGroup);
        this.name = wareGroup.getName();

        if (cascade) {
            this.company = new CompanyDTO(wareGroup.getCompany(), false);
            this.wares = wareGroup.getWares().stream().map(ware -> new WareDTO(ware, false)).collect(Collectors.toList());
        }
    }

    public WareGroup construct() {
        final WareGroup wareGroup = new WareGroup(this);
        wareGroup.setName(name);
        wareGroup.setCompany(company != null ? company.construct() : null);
        if (wares != null) {
            wares.forEach(wareDTO -> wareGroup.getWares().add(wareDTO.construct()));
        }
        return wareGroup;
    }

}
