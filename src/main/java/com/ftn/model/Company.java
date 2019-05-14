package com.ftn.model;

import com.ftn.constants.Sql;
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
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "company" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class Company extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 9, unique = true)
    private long pib;

    private String address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<WareGroup> wareGroups = new ArrayList<>();

    @ManyToOne
    private Location location;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<BusinessPartner> businessPartners = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Warehouse> warehouses = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<BusinessYear> businessYears = new ArrayList<>();

    public Company(Company company) {
    }

    //brisanje
    /*public Company(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public void merge(CompanyDTO companyDTO) {
        this.name = companyDTO.getName();
        this.pib = companyDTO.getPib();
        this.address = companyDTO.getAddress();
        if (companyDTO.getLocation() != null) {
            this.location = companyDTO.getLocation().construct();
        }
    }*/
}
