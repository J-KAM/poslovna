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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = Sql.UPDATE + "user" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class Employee extends User {

    @ManyToOne
    private Location location;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Warehouse> warehouses = new ArrayList<>();

    @ManyToOne // TODO: Find a way to make this mandatory if possible
    private Company company;

    public Employee(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public Employee(UserDTO userDTO) {
        super(userDTO);
    }
}