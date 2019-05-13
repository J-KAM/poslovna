package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.dto.BaseDTO;
import com.ftn.model.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by Alex on 4/19/17.
 */
@Entity
@Table(name = "[user]")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "user" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseModel {

    public enum Role {
        ADMIN,
        EMPLOYEE
    }

    @Column(nullable = false, length = 13)
    private String jmbg;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String address;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean enabled;

    public User(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public User(UserDTO userDTO) {
        super(userDTO);
        this.jmbg = userDTO.getJmbg();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.address = userDTO.getAddress();
        this.username = userDTO.getUsername();
        this.role = Role.valueOf(userDTO.getRole());
    }
}
