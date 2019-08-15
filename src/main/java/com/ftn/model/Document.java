package com.ftn.model;

import com.ftn.constants.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
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

    @ManyToOne(optional = false)
    private BusinessYear businessYear;

    @ManyToOne(optional = false)
    private Warehouse warehouse;

    @ManyToOne
    private Warehouse innerWarehouse;

    @ManyToOne
    private BusinessPartner businessPartner;

    //dodato iz dto sloja
    @Transient
    private boolean reverse;

}
