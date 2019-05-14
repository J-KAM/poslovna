package com.ftn.service;

import com.ftn.model.Document;

import java.util.List;

/**
 * Created by Olivera on 31.5.2017..
 */
public interface DocumentService {

    List<Document> read();

    List<Document> readByWarehouse(Long id);

    Document create(Document document);

    Document update(Long id, Document document);

    void delete(Long id);
}
