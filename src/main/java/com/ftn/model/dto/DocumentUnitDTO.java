package com.ftn.model.dto;

import com.ftn.model.DocumentUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by Alex on 5/20/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DocumentUnitDTO extends BaseDTO {

    @NotNull
    private double quantity;

    @NotNull
    private double price;

    private double value;

    @NotNull
    private DocumentDTO document;

    @NotNull
    private WareDTO ware;

    public DocumentUnitDTO(DocumentUnit documentUnit) {
        this(documentUnit, true);
    }

    public DocumentUnitDTO(DocumentUnit documentUnit, boolean cascade) {
        super(documentUnit);
        this.quantity = documentUnit.getQuantity();
        this.price = documentUnit.getPrice();
        this.value = documentUnit.getValue();
        if (cascade) {
            this.document = new DocumentDTO(documentUnit.getDocument(), false);
            this.ware = new WareDTO(documentUnit.getWare(), false);
        }
    }

    public DocumentUnit construct() {
        final DocumentUnit documentUnit = new DocumentUnit(this);
        documentUnit.setQuantity(quantity);
        documentUnit.setPrice(price);
        documentUnit.setValue(value);
        documentUnit.setDocument(document != null ? document.construct() : null);
        documentUnit.setWare(ware != null ? ware.construct() : null);
        return documentUnit;
    }
}
