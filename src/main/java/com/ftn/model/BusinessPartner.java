package com.ftn.model;

import com.fasterxml.jackson.annotation.*;
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
@EqualsAndHashCode(callSuper = true, exclude = {"company", "location"})
@NoArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "business_partner" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class BusinessPartner extends BaseModel {

    public enum PartnershipType {
        BUYER,
        SUPPLIER,
        ALL
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartnershipType partnershipType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 9, unique = true)
    private long pib;

    private String address;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("businessPartners")
    @JsonManagedReference
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties("businessPartners")
    @JsonManagedReference
    private Location location;

    @OneToMany(mappedBy = "businessPartner", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Document> documents = new ArrayList<>();

}
