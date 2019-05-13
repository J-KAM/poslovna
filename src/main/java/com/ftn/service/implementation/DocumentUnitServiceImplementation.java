package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Document;
import com.ftn.model.DocumentUnit;
import com.ftn.model.dto.DocumentUnitDTO;
import com.ftn.repository.DocumentDao;
import com.ftn.repository.DocumentUnitDao;
import com.ftn.repository.WareDao;
import com.ftn.service.DocumentUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<DocumentUnitDTO> read() {
        return documentUnitDao.findAll().stream().map(DocumentUnitDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<DocumentUnitDTO> read(Long documentId) {
        List<DocumentUnitDTO> documentUnitDTOS = new ArrayList<>();
        List<DocumentUnit> documentUnits = documentUnitDao.findByDocumentId(documentId);
        for(DocumentUnit documentUnit : documentUnits){
            DocumentUnitDTO documentUnitDTO = new DocumentUnitDTO(documentUnit, true);
            documentUnitDTOS.add(documentUnitDTO);
        }
        return documentUnitDTOS;
    }

    @Override
    public DocumentUnitDTO create(DocumentUnitDTO documentUnitDTO) {
        if (documentUnitDao.findById(documentUnitDTO.getId()).isPresent()) {
            throw new BadRequestException();
        }
        final DocumentUnit documentUnit = documentUnitDTO.construct();
        documentUnitDao.save(documentUnit);
        return new DocumentUnitDTO(documentUnit);
    }

    @Override
    public DocumentUnitDTO update(Long id, DocumentUnitDTO documentUnitDTO) {
        final DocumentUnit documentUnit = documentUnitDao.findById(id).orElseThrow(NotFoundException::new);
        documentUnit.merge(documentUnitDTO);
        documentUnitDao.save(documentUnit);
        return new DocumentUnitDTO(documentUnit);
    }

    @Override
    public void delete(Long id) {
        final DocumentUnit documentUnit = documentUnitDao.findById(id).orElseThrow(NotFoundException::new);
        documentUnitDao.delete(documentUnit);
    }
}
