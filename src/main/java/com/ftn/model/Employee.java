package com.ftn.model;

import com.ftn.constants.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "company")
@SQLDelete(sql = Sql.UPDATE + "user" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class Employee extends User {

    @ManyToOne
    private Location location;

    @ManyToOne // TODO: Find a way to make this mandatory if possible
    private Company company;

    public Employee(User user) {
        super(user);
    }
}