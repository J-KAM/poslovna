package com.ftn.model.dto;

import com.ftn.model.Warehouse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 30.5.2017..
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class WarehouseDTO extends BaseDTO {

    @NotNull
    private String name;

    private EmployeeDTO employee;

    @NotNull
    private CompanyDTO company;

    private List<WarehouseCardDTO> warehouseCards = new ArrayList<>();

    private List<DocumentDTO> documents = new ArrayList<>();

    private List<DocumentDTO> innerDocuments = new ArrayList<>();

    public WarehouseDTO(Warehouse warehouse) {
        this(warehouse, true);
    }

    public WarehouseDTO(Warehouse warehouse, boolean cascade) {
        super(warehouse);
        this.name = warehouse.getName();

        if (cascade) {
            this.company = new CompanyDTO(warehouse.getCompany(), false);
            this.employee = new EmployeeDTO(warehouse.getEmployee(), false);
            this.documents = warehouse.getDocuments().stream().map(document -> new DocumentDTO(document, false)).collect(Collectors.toList());
            this.innerDocuments = warehouse.getInnerDocuments().stream().map(innerDocument -> new DocumentDTO(innerDocument, false)).collect(Collectors.toList());
            this.warehouseCards = warehouse.getWarehouseCards().stream().map(warehouseCard -> new WarehouseCardDTO(warehouseCard, false)).collect(Collectors.toList());
        }

    }

    public Warehouse construct() {
        final Warehouse warehouse = new Warehouse(this);
        warehouse.setName(name);
        if (employee != null) {
            warehouse.setEmployee(employee.construct());
        }
        warehouse.setCompany(company != null ? company.construct() : null);
        if (warehouseCards != null) {
            warehouseCards.forEach(warehouseCardDTO -> warehouse.getWarehouseCards().add(warehouseCardDTO.construct()));
        }
        if (documents != null) {
            documents.forEach(documentDTO -> warehouse.getDocuments().add(documentDTO.construct()));
        }
        if (innerDocuments != null) {
            innerDocuments.forEach(documentDTO -> warehouse.getInnerDocuments().add(documentDTO.construct()));
        }

        return warehouse;
    }
}
