package com.ftn.model.dto;

import com.ftn.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Alex on 5/18/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserDTO extends BaseDTO {

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
    private String role;

    public UserDTO(User user) {
        super(user);
        this.jmbg = user.getJmbg();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.address = user.getAddress();
        this.username = user.getUsername();
        this.role = user.getRole().toString();
    }
}
