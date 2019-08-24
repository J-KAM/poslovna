package com.ftn.model;

import com.ftn.constants.Sql;
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

    @Column
    private double value;

    @ManyToOne(optional = false)
    private Document document;

    @ManyToOne(optional = false)
    private Ware ware;

}
