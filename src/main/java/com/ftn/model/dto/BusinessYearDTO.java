package com.ftn.model.dto;

import com.ftn.model.BusinessYear;
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
public class BusinessYearDTO extends BaseDTO {

    @NotNull
    private int year;

    @NotNull
    private boolean closed;

    @NotNull
    private CompanyDTO company;

    private List<DocumentDTO> documents = new ArrayList<>();

    private List<WarehouseCardDTO> warehouseCards = new ArrayList<>();


    public BusinessYearDTO(BusinessYear businessYear) {
        this(businessYear, true);
    }

    public BusinessYearDTO(BusinessYear businessYear, boolean cascade) {
        super(businessYear);
        this.year = businessYear.getYear();
        this.closed = businessYear.isClosed();

        if (cascade) {
            this.company = new CompanyDTO(businessYear.getCompany(), false);
            documents = businessYear.getDocuments().stream().map(document -> new DocumentDTO(document, false)).collect(Collectors.toList());
            warehouseCards = businessYear.getWarehouseCards().stream().map(warehouseCard -> new WarehouseCardDTO(warehouseCard, false)).collect(Collectors.toList());
        }
    }

    public BusinessYear construct() {
        final BusinessYear businessYear = new BusinessYear(this);
        businessYear.setYear(year);
        businessYear.setClosed(closed);
        businessYear.setCompany(company != null ? company.construct() : null);
        if (documents != null) {
            documents.forEach(documentDTO -> businessYear.getDocuments().add(documentDTO.construct()));
        }
        if (warehouseCards != null) {
            warehouseCards.forEach(warehouseCardDTO -> businessYear.getWarehouseCards().add(warehouseCardDTO.construct()));
        }
        return businessYear;
    }
}
