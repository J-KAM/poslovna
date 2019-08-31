package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.enums.Role;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "user" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseModel {

    @Column(nullable = false, length = 13)
    private String jmbg;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String address;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne
    private Location location;

    @ManyToOne(optional = false)
    private Company company;

    @Column(nullable = false)
    private boolean enabled;

}
