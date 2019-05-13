package com.ftn.model.dto;

import com.ftn.model.Company;
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
 * Created by Jasmina on 21/05/2017.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CompanyDTO extends BaseDTO {

    @NotNull
    private String name;

    @NotNull
    @Min(100000000)
    @Max(999999999)
    private long pib;

    private String address;

    private List<WareGroupDTO> wareGroups = new ArrayList<>();

    private LocationDTO location;

    private List<BusinessPartnerDTO> businessPartners = new ArrayList<>();

    private List<EmployeeDTO> employees = new ArrayList<>();

    private List<WarehouseDTO> warehouses = new ArrayList<>();

    private List<BusinessYearDTO> businessYears = new ArrayList<>();

    public CompanyDTO(Company company) {
        this(company, true);
    }

    public CompanyDTO(Company company, boolean cascade) {
        super(company);
        this.name = company.getName();
        this.pib = company.getPib();
        this.address = company.getAddress();
        if (cascade) {
            this.location = new LocationDTO(company.getLocation(), false);
            this.wareGroups = company.getWareGroups().stream().map(wareGroup -> new WareGroupDTO(wareGroup, false)).collect(Collectors.toList());
            this.businessPartners = company.getBusinessPartners().stream().map(businessPartner -> new BusinessPartnerDTO(businessPartner, false)).collect(Collectors.toList());
            this.warehouses = company.getWarehouses().stream().map(warehouse -> new WarehouseDTO(warehouse, false)).collect(Collectors.toList());
            this.employees = company.getEmployees().stream().map(employee -> new EmployeeDTO(employee, false)).collect(Collectors.toList());
            this.businessYears = company.getBusinessYears().stream().map(businessYear -> new BusinessYearDTO(businessYear, false)).collect(Collectors.toList());
        }
    }

    public Company construct() {
        final Company company = new Company(this);
        company.setName(name);
        company.setPib(pib);
        company.setAddress(address);
        company.setLocation(location != null ? location.construct() : null);
        if (wareGroups != null) {
            wareGroups.forEach(wareGroupDTO -> company.getWareGroups().add(wareGroupDTO.construct()));
        }
        if (businessPartners != null) {
            businessPartners.forEach(businessPartnerDTO -> company.getBusinessPartners().add(businessPartnerDTO.construct()));
        }
        if (warehouses != null) {
            warehouses.forEach(warehouseDTO -> company.getWarehouses().add(warehouseDTO.construct()));
        }
        if (employees != null) {
            employees.forEach(employeeDTO -> company.getEmployees().add(employeeDTO.construct()));
        }
        if(businessYears != null) {
            businessYears.forEach(businessYearDTO -> company.getBusinessYears().add(businessYearDTO.construct()));
        }
        return company;
    }

}
