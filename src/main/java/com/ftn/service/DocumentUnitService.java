package com.ftn.service;

import com.ftn.model.DocumentUnit;

import java.util.List;

/**
 * Created by Alex on 5/20/17.
 */
public interface DocumentUnitService {

    List<DocumentUnit> read();

    DocumentUnit read(Long id);

    List<DocumentUnit> readByDocumentId(Long documentId);

    DocumentUnit create(DocumentUnit documentUnit);

    DocumentUnit update(Long id, DocumentUnit documentUnit);

    void delete(Long id);
}
