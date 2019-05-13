package com.ftn.model;

import com.ftn.constants.Sql;
import com.ftn.model.dto.BaseDTO;
import com.ftn.model.dto.DocumentUnitDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Alex on 5/15/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SQLDelete(sql = Sql.UPDATE + "document_unit" + Sql.SOFT_DELETE)
@Where(clause = Sql.ACTIVE)
public class DocumentUnit extends BaseModel {

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private double price;

    private double value;

    @ManyToOne(optional = false)
    private Document document;

    @ManyToOne(optional = false)
    private Ware ware;

    public DocumentUnit(BaseDTO baseDTO) {
        super(baseDTO);
    }

    public void merge(DocumentUnitDTO documentUnitDTO) {
        this.quantity = documentUnitDTO.getQuantity();
        this.price = documentUnitDTO.getPrice();
        this.value = documentUnitDTO.getValue();
        if (documentUnitDTO.getWare() != null) {
            this.ware = documentUnitDTO.getWare().construct();
        }
    }
}
