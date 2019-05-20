package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.DocumentUnit;
import com.ftn.repository.DocumentDao;
import com.ftn.repository.DocumentUnitDao;
import com.ftn.repository.WareDao;
import com.ftn.service.DocumentUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Alex on 5/20/17.
 */
@Service
public class DocumentUnitServiceImplementation implements DocumentUnitService {

    private final DocumentUnitDao documentUnitDao;

    private final DocumentDao documentDao;

    private final WareDao wareDao;

    @Autowired
    public DocumentUnitServiceImplementation(DocumentUnitDao documentUnitDao, DocumentDao documentDao, WareDao wareDao) {
        this.documentUnitDao = documentUnitDao;
        this.documentDao = documentDao;
        this.wareDao = wareDao;
    }

    @Override
    public List<DocumentUnit> read() {
        return documentUnitDao.findAll();
    }

    @Override
    public List<DocumentUnit> read(Long documentId) {
        List<DocumentUnit> documentUnits = documentUnitDao.findByDocumentId(documentId);
        return documentUnits;
    }

    @Override
    public DocumentUnit create(DocumentUnit documentUnit) {
        if (documentUnitDao.findById(documentUnit.getId()).isPresent()) {
            throw new BadRequestException();
        }
        return documentUnitDao.save(documentUnit);
    }

    @Override
    public DocumentUnit update(Long id, DocumentUnit documentUnit) {
        documentUnitDao.findById(id).orElseThrow(NotFoundException::new);
        documentUnit.setId(id);
        return documentUnitDao.save(documentUnit);
    }

    @Override
    public void delete(Long id) {
        final DocumentUnit documentUnit = documentUnitDao.findById(id).orElseThrow(NotFoundException::new);
        documentUnitDao.delete(documentUnit);
    }
}
