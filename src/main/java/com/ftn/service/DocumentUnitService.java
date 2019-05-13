package com.ftn.service;

import com.ftn.model.dto.DocumentUnitDTO;

import java.util.List;

/**
 * Created by Alex on 5/20/17.
 */
public interface DocumentUnitService {

    List<DocumentUnitDTO> read();

    List<DocumentUnitDTO> read(Long documentId);

    DocumentUnitDTO create(DocumentUnitDTO documentUnitDTO);

    DocumentUnitDTO update(Long id, DocumentUnitDTO documentUnitDTO);

    void delete(Long id);
}
