package com.ftn.model.dto;

import com.ftn.model.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jasmina on 20/05/2017.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LocationDTO extends BaseDTO {

    @NotNull
    private String name;

    @NotNull
    private int ptt;

    private List<CompanyDTO> companies = new ArrayList<>();

    private List<BusinessPartnerDTO> businessPartners = new ArrayList<>();

    private List<EmployeeDTO> employees = new ArrayList<>();

    public LocationDTO(Location location) {
        this(location, true);
    }

    public LocationDTO(Location location, boolean cascade) {
        super(location);
        this.name = location.getName();
        this.ptt = location.getPtt();
        if (cascade) {
            this.companies = location.getCompanies().stream().map(company -> new CompanyDTO(company, false)).collect(Collectors.toList());
            this.businessPartners = location.getBusinessPartners().stream().map(businessPartner -> new BusinessPartnerDTO(businessPartner, false)).collect(Collectors.toList());
            this.employees = location.getEmployees().stream().map(employee -> new EmployeeDTO(employee, false)).collect(Collectors.toList());
        }
    }

    public Location construct() {
        final Location location = new Location(this);
        location.setName(name);
        location.setPtt(ptt);
        if (companies != null) {
            companies.forEach(companyDTO -> location.getCompanies().add(companyDTO.construct()));
        }
        if (businessPartners != null) {
            businessPartners.forEach(businessPartnerDTO -> location.getBusinessPartners().add(businessPartnerDTO.construct()));
        }
        if (employees != null) {
            employees.forEach(employeeDTO -> location.getEmployees().add(employeeDTO.construct()));
        }
        return location;
    }


}
