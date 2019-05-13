package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.model.dto.BusinessYearDTO;
import com.ftn.model.dto.WarehouseCardAnalyticsDTO;
import com.ftn.repository.BusinessYearDao;
import com.ftn.repository.DocumentDao;
import com.ftn.repository.WarehouseCardAnalyticsDao;
import com.ftn.repository.WarehouseCardDao;
import com.ftn.service.AuthenticationService;
import com.ftn.service.BusinessYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 30.5.2017.
 */
@Service
public class BusinessYearServiceImplementation implements BusinessYearService {

    private final BusinessYearDao businessYearDao;

    private final DocumentDao documentDao;

    private final WarehouseCardDao warehouseCardDao;

    private final WarehouseCardAnalyticsDao warehouseCardAnalyticsDao;

    private final AuthenticationService authenticationService;

    @Autowired
    public BusinessYearServiceImplementation(BusinessYearDao businessYearDao, DocumentDao documentDao, WarehouseCardDao warehouseCardDao, WarehouseCardAnalyticsDao warehouseCardAnalyticsDao, AuthenticationService authenticationService) {
        this.businessYearDao = businessYearDao;
        this.documentDao = documentDao;
        this.warehouseCardDao = warehouseCardDao;
        this.warehouseCardAnalyticsDao = warehouseCardAnalyticsDao;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<BusinessYearDTO> read() {
        Employee employee = authenticationService.getCurrentUser();
        return businessYearDao.findByCompanyId(employee.getCompany().getId()).stream().map(BusinessYearDTO::new).collect(Collectors.toList());
    }

    @Override
    public BusinessYearDTO create(BusinessYearDTO businessYearDTO) {
        if (businessYearDao.findById(businessYearDTO.getId()).isPresent()) {
            throw new BadRequestException();
        }
        final BusinessYear businessYear = businessYearDTO.construct();
        open(businessYear);
        return new BusinessYearDTO(businessYear);
    }

    @Override
    public BusinessYearDTO update(Long id, BusinessYearDTO businessYearDTO) {
        final BusinessYear businessYear = businessYearDao.findById(id).orElseThrow(NotFoundException::new);
        businessYear.merge(businessYearDTO);
        businessYearDao.save(businessYear);
        return new BusinessYearDTO(businessYear);
    }

    @Override
    public void delete(Long id) {
        final BusinessYear businessYear = businessYearDao.findById(id).orElseThrow(NotFoundException::new);
        businessYearDao.delete(businessYear);
    }

    @Override
    public boolean active(BusinessYearDTO businessYearDTO) {
        final BusinessYear businessYear = businessYearDao.findById(businessYearDTO.getId()).orElseThrow(NotFoundException::new);
        return businessYear.isActive();
    }

    private void open(BusinessYear businessYear) {
        final Optional<BusinessYear> previousBusinessYear = businessYearDao.findFirstByOrderByYearDesc();
        if (!previousBusinessYear.isPresent()) {
            return;
        }
        if (previousBusinessYear.get().getYear() > businessYear.getYear()) {
            throw new BadRequestException();
        }
        // If there are pending documents from the previous business year, this business year cannot be opened
        final List<Document> documents = documentDao.findByWarehouseEmployeeIdAndBusinessYearId(authenticationService.getCurrentUser().getId(), previousBusinessYear.get().getId());
        if (documents.stream().anyMatch(document -> document.getStatus().equals(Document.Status.PENDING))) {
            throw new BadRequestException();
        }
        businessYearDao.save(businessYear);
        // Closing previous business year
        previousBusinessYear.get().setClosed(true);
        businessYearDao.save(previousBusinessYear.get());
        final List<WarehouseCard> warehouseCards = warehouseCardDao.findByBusinessYearIdAndWarehouseEmployeeId(previousBusinessYear.get().getId(), authenticationService.getCurrentUser().getId());
        final List<WarehouseCard> newWarehouseCards = new ArrayList<>();
        warehouseCards.forEach(warehouseCard -> {
            final WarehouseCard newWarehouseCard = warehouseCard.constructNextYearCard();
            newWarehouseCard.setBusinessYear(businessYear);
            newWarehouseCards.add(newWarehouseCard);
        });

        //Creating analytics for new warehouse cards
        final List<WarehouseCard> savedWarehouseCards = warehouseCardDao.save(newWarehouseCards);
        savedWarehouseCards.forEach(savedwarehouseCard -> {
            final WarehouseCardAnalytics warehouseCardAnalytics = new WarehouseCardAnalytics();
            warehouseCardAnalytics.setTrafficType(WarehouseCardAnalytics.TrafficType.INITIAL_STATE);
            warehouseCardAnalytics.setDirection(WarehouseCardAnalytics.Direction.INCOMING);
            warehouseCardAnalytics.setQuantity(savedwarehouseCard.getTotalQuantity());
            warehouseCardAnalytics.setValue(savedwarehouseCard.getTotalValue());
            warehouseCardAnalytics.setAveragePrice(savedwarehouseCard.getAveragePrice());
            warehouseCardAnalytics.setWarehouseCard(savedwarehouseCard);

            final WarehouseCardAnalytics savedWarehouseCardAnalytics = warehouseCardAnalyticsDao.save(warehouseCardAnalytics);
            savedwarehouseCard.getWarehouseCardAnalytics().add(savedWarehouseCardAnalytics);
            warehouseCardDao.save(savedwarehouseCard);
        });
    }
}
