package com.ftn.model.dto;

import com.ftn.model.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 5/31/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EmployeeDTO extends UserDTO {

    private LocationDTO locationDTO;

    private List<WarehouseDTO> warehouses = new ArrayList<>();

    private CompanyDTO companyDTO;

    public EmployeeDTO(Employee employee) {
        this(employee, true);
    }

    public EmployeeDTO(Employee employee, boolean cascade) {
        super(employee);
        if (cascade) {
            this.locationDTO = employee.getLocation() != null ? new LocationDTO(employee.getLocation()) : null;
            this.companyDTO = employee.getCompany() != null ? new CompanyDTO(employee.getCompany()) : null;
            this.warehouses = employee.getWarehouses().stream().map(warehouse -> new WarehouseDTO(warehouse, false)).collect(Collectors.toList());
        }
    }

    public Employee construct() {
        final Employee employee = new Employee(this);
        employee.setLocation(locationDTO != null ? locationDTO.construct() : null);
        employee.setCompany(companyDTO != null ? companyDTO.construct() : null);
        if (warehouses != null) {
            warehouses.forEach(warehouseDTO -> employee.getWarehouses().add(warehouseDTO.construct()));
        }
        return employee;
    }
}
