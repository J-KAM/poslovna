package com.ftn.model.dto;

import com.ftn.model.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by Alex on 5/31/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EmployeeDTO extends UserDTO {

    public EmployeeDTO(Employee employee) {
        this(employee, true);
    }

    public EmployeeDTO(Employee employee, boolean cascade) {
        super(employee);
    }

    public Employee construct() {
        final Employee employee = new Employee(this);
        return employee;
    }
}
