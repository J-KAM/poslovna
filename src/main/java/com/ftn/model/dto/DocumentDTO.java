package com.ftn.model.dto;

import com.ftn.model.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Olivera on 30.5.2017..
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DocumentDTO extends BaseDTO {

    @NotNull
    private String documentType;

    private Long serialNumber;

    private Date establishmentDate;

    private Date bookingDate;

    private String status;

    private List<DocumentUnitDTO> documentUnits = new ArrayList<>();

    @NotNull
    private BusinessYearDTO businessYear;

    @NotNull
    private WarehouseDTO warehouse;

    private WarehouseDTO innerWarehouse;

    private BusinessPartnerDTO businessPartner;

    private boolean reverse;

    public DocumentDTO(Document document) {
        this(document, true);
    }

    public DocumentDTO(Document document, boolean cascade) {
        super(document);
        this.documentType = document.getDocumentType().toString();
        this.serialNumber = document.getSerialNumber();
        this.establishmentDate = document.getEstablishmentDate();
        this.bookingDate = document.getBookingDate();
        this.status = document.getStatus().toString();
        if (cascade) {
            this.documentUnits = document.getDocumentUnits().stream().map(documentUnit -> new DocumentUnitDTO(documentUnit, false)).collect(Collectors.toList());
            this.businessYear = new BusinessYearDTO(document.getBusinessYear(), false);
            this.warehouse = new WarehouseDTO(document.getWarehouse(), false);
            if (document.getInnerWarehouse() != null)
                this.innerWarehouse = new WarehouseDTO(document.getInnerWarehouse(), false);
            else
                this.businessPartner = new BusinessPartnerDTO(document.getBusinessPartner(), false);
        }
    }

    public Document construct() {
        final Document document = new Document(this);
        document.setDocumentType(Document.DocumentType.valueOf(documentType));
        document.setSerialNumber(serialNumber);
        document.setEstablishmentDate(establishmentDate);
        document.setBookingDate(bookingDate);
        if (status != null) {
            document.setStatus(Document.Status.valueOf(status));
        }
        if (documentUnits != null) {
            documentUnits.forEach(documentUnitDTO -> document.getDocumentUnits().add(documentUnitDTO.construct()));
        }
        if (businessYear != null) {
            document.setBusinessYear(businessYear.construct());
        }
        if (warehouse != null) {
            document.setWarehouse(warehouse.construct());
        }
        if (innerWarehouse != null) {
            document.setInnerWarehouse(innerWarehouse.construct());
        }
        if (businessPartner != null) {
            document.setBusinessPartner(businessPartner.construct());
        }
        return document;
    }

}
