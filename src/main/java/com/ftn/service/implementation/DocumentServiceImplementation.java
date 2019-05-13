package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.model.dto.DocumentDTO;
import com.ftn.repository.DocumentDao;
import com.ftn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<DocumentDTO> read() {
        final User user = authenticationService.getCurrentUser();
        return documentDao.findByWarehouseEmployeeId(user.getId()).stream().map(DocumentDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<DocumentDTO> readByWarehouse(Long id) {
        return documentDao.findByWarehouseId(id).stream().map(DocumentDTO::new).collect(Collectors.toList());
    }

    @Override
    public DocumentDTO create(DocumentDTO documentDTO) {
        if (documentDao.findById(documentDTO.getId()).isPresent()) {
            throw new BadRequestException();
        }
        final Document document = documentDTO.construct();
        document.setStatus(Document.Status.PENDING);
        document.setEstablishmentDate(new Date());
        document.setSerialNumber(documentDao.count() + 1);
        documentDao.save(document);
        return new DocumentDTO(document);
    }

    @Override
    public DocumentDTO update(Long id, DocumentDTO documentDTO) {
        final Document document = getDocument(id);
        document.merge(documentDTO);
        documentDao.save(document);
        return new DocumentDTO(document);
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
