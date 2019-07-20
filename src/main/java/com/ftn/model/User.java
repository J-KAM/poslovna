package com.ftn.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ftn.constants.Sql;
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

    public User(User user) {
        this.jmbg = user.getJmbg();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.address = user.getAddress();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
