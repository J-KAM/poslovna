package com.ftn.model.dto;

import com.ftn.model.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Alex on 5/18/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterUserDTO extends BaseDTO {

    @NotNull
    @Size(min = 13, max = 13)
    @Pattern(regexp = "[0-9]*")
    private String jmbg;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String address;

    @NotNull
    private String username;

    @NotNull
    private String password;

    public Employee construct() {
        final Employee employee = new Employee(this);
        employee.setJmbg(jmbg);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setAddress(address);
        employee.setUsername(username);
        employee.setPassword(password);
        return employee;
    }
}
