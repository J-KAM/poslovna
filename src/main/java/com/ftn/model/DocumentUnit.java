package com.ftn.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@EqualsAndHashCode(callSuper = true, exclude = {"document", "ware"})
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
    @JsonIgnoreProperties("documentUnits")
    @JsonManagedReference
    private Document document;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("documentUnits")
    @JsonManagedReference
    private Ware ware;

}
