package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.dto.BaseDTO;
import com.ftn.model.dto.DocumentDTO;
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

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<DocumentUnit> documentUnits = new ArrayList<>();

    @ManyToOne(optional = false)
    private BusinessYear businessYear;

    @ManyToOne(optional = false)
    private Warehouse warehouse;

    @ManyToOne
    private Warehouse innerWarehouse;

    @ManyToOne
    private BusinessPartner businessPartner;

    public Document(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public void merge(DocumentDTO documentDTO) {
        this.documentType = DocumentType.valueOf(documentDTO.getDocumentType());
        this.serialNumber = documentDTO.getSerialNumber();
        this.establishmentDate = documentDTO.getEstablishmentDate();
        this.bookingDate = documentDTO.getBookingDate();
        this.status = Status.valueOf(documentDTO.getStatus());
        this.businessYear = documentDTO.getBusinessYear().construct();
        this.warehouse = documentDTO.getWarehouse().construct();
        if (documentDTO.getInnerWarehouse() != null)
            this.innerWarehouse = documentDTO.getInnerWarehouse().construct();
        else
            this.businessPartner = documentDTO.getBusinessPartner().construct();

    }
}
