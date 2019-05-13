package com.ftn.service;

import com.ftn.model.dto.DocumentDTO;

import java.util.List;

/**
 * Created by Olivera on 31.5.2017..
 */
public interface DocumentService {

    List<DocumentDTO> read();

    List<DocumentDTO> readByWarehouse(Long id);

    DocumentDTO create(DocumentDTO documentDTO);

    DocumentDTO update(Long id, DocumentDTO documentDTO);

    void delete(Long id);
}
