package com.ftn.model.dto;

import com.ftn.model.BusinessPartner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 21.5.2017..
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BusinessPartnerDTO extends BaseDTO {

    @NotNull
    private String partnershipType;

    @NotNull
    private String name;

    @NotNull
    @Min(100000000)
    @Max(999999999)
    private long pib;

    private String address;

    @NotNull
    private CompanyDTO company;

    private LocationDTO location;

    private List<DocumentDTO> documents = new ArrayList<>();

    public BusinessPartnerDTO(BusinessPartner businessPartner) {
        this(businessPartner, true);
    }

    public BusinessPartnerDTO(BusinessPartner businessPartner, boolean cascade) {
        super(businessPartner);
        this.partnershipType = businessPartner.getPartnershipType().toString();
        this.name = businessPartner.getName();
        this.pib = businessPartner.getPib();
        this.address = businessPartner.getAddress();
        if (cascade) {
            this.documents = businessPartner.getDocuments().stream().map(document -> new DocumentDTO(document, false)).collect(Collectors.toList());
            this.company = new CompanyDTO(businessPartner.getCompany(), false);
            this.location = new LocationDTO(businessPartner.getLocation(), false);
        }
    }

    public BusinessPartner construct() {
        final BusinessPartner businessPartner = new BusinessPartner(this);
        businessPartner.setPartnershipType(BusinessPartner.PartnershipType.valueOf(partnershipType));
        businessPartner.setName(name);
        businessPartner.setPib(pib);
        businessPartner.setAddress(address);
        businessPartner.setCompany(company != null ? company.construct() : null);
        businessPartner.setLocation(location != null ? location.construct() : null);
        if (documents != null) {
            documents.forEach(documentDTO -> businessPartner.getDocuments().add(documentDTO.construct()));
        }
        return businessPartner;
    }
}
