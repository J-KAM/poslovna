package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Document;
import com.ftn.model.User;
import com.ftn.repository.DocumentDao;
import com.ftn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Olivera on 31.5.2017..
 */
@Service
public class DocumentServiceImplementation implements DocumentService {

    private final DocumentDao documentDao;

    private final AuthenticationService authenticationService;

    private final DocumentUnitService documentUnitService;

    private final WarehouseCardService warehouseCardService;

    private final WarehouseCardAnalyticsService warehouseCardAnalyticsService;

    @Autowired
    public DocumentServiceImplementation(DocumentDao documentDao, AuthenticationService authenticationService,
                                         DocumentUnitService documentUnitService, WarehouseCardService warehouseCardService, WarehouseCardAnalyticsService warehouseCardAnalyticsService) {
        this.documentDao = documentDao;
        this.authenticationService = authenticationService;
        this.documentUnitService = documentUnitService;
        this.warehouseCardService = warehouseCardService;
        this.warehouseCardAnalyticsService = warehouseCardAnalyticsService;
    }

    @Override
    public List<Document> read() {
        final User user = authenticationService.getCurrentUser();
        return documentDao.findByWarehouseEmployeeId(user.getId());
    }

    @Override
    public List<Document> readByWarehouse(Long id) {
        return documentDao.findByWarehouseId(id);
    }

    @Override
    public Document create(Document document) {
        if (documentDao.findById(document.getId()).isPresent()) {
            throw new BadRequestException();
        }
        document.setStatus(Document.Status.PENDING);
        document.setEstablishmentDate(new Date());
        document.setSerialNumber(documentDao.count() + 1);
        documentDao.save(document);
        return document;
    }

    @Override
    public Document update(Long id, Document document) {
        getDocument(id);
        document.setId(id);
        return documentDao.save(document);
    }

    @Override
    public void delete(Long id) {
        final Document document = getDocument(id);
        documentDao.delete(document);
    }

    private Document getDocument(Long id) {
        final User user = authenticationService.getCurrentUser();
        return documentDao.findByIdAndWarehouseEmployeeId(id, user.getId()).orElseThrow(NotFoundException::new);
    }
}
