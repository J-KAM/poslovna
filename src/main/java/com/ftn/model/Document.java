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
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"businessYear", "warehouse", "innerWarehouse", "businessPartner"})
@NoArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "document" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class Document extends BaseModel {

    public enum DocumentType {
        RECEIPT,
        DISPATCH,
        INTER_WAREHOUSE_TRAFFIC
    }

    public enum Status {
        PENDING,
        BOOKED,
        REVERSED
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    @Column(nullable = false)
    private Long serialNumber;

    @Column(nullable = false)
    private Date establishmentDate;

    private Date bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<DocumentUnit> documentUnits = new ArrayList<>();

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("documents")
    @JsonManagedReference
    private BusinessYear businessYear;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("documents")
    @JsonManagedReference
    private Warehouse warehouse;

    @ManyToOne
    @JsonIgnoreProperties("documents")
    @JsonManagedReference
    private Warehouse innerWarehouse;

    @ManyToOne
    @JsonIgnoreProperties("documents")
    @JsonManagedReference
    private BusinessPartner businessPartner;

    //dodato iz dto sloja
    private boolean reverse;

}
