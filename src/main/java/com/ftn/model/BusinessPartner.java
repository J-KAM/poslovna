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
@Data
@EqualsAndHashCode(callSuper = true)
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
    private Company company;

    @ManyToOne
    private Location location;

}
