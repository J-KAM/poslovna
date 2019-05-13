package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Document;
import com.ftn.model.WarehouseCardAnalytics;
import com.ftn.model.dto.DocumentDTO;
import com.ftn.model.dto.DocumentUnitDTO;
import com.ftn.model.dto.WarehouseCardAnalyticsDTO;
import com.ftn.model.dto.WarehouseCardDTO;
import com.ftn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Jasmina on 14/06/2017.
 */
@Service
public class BookingServiceImplementation implements BookingService {

    private final DocumentService documentService;

    private final DocumentUnitService documentUnitService;

    private final WarehouseCardService warehouseCardService;

    private final WarehouseCardAnalyticsService warehouseCardAnalyticsService;

    private final BusinessYearService businessYearService;

    @Autowired
    public BookingServiceImplementation(DocumentService documentService, DocumentUnitService documentUnitService,
                    WarehouseCardService warehouseCardService, WarehouseCardAnalyticsService warehouseCardAnalyticsService,
                    BusinessYearService businessYearService){
        this.documentService = documentService;
        this.documentUnitService = documentUnitService;
        this.warehouseCardService = warehouseCardService;
        this.warehouseCardAnalyticsService = warehouseCardAnalyticsService;
        this.businessYearService = businessYearService;
    }

    @Override
    public DocumentDTO book(Long id, DocumentDTO documentDTO) {
        List<DocumentUnitDTO> unitDTOS = documentUnitService.read(id);

        if(unitDTOS.size() == 0){
            throw new BadRequestException();
        }

        //Check if year is active
        boolean active = businessYearService.active(documentDTO.getBusinessYear());
        if(!active){
            throw new BadRequestException();
        }

        for(DocumentUnitDTO documentUnitDTO : unitDTOS){
            WarehouseCardDTO warehouseCardDTO = warehouseCardService.read(documentUnitDTO.getWare(),documentDTO.getBusinessYear(), documentDTO.getWarehouse());
            if(warehouseCardDTO == null){
                warehouseCardDTO = makeCardAndAnalytics(warehouseCardDTO, documentUnitDTO, documentDTO);
            }else {
                warehouseCardDTO = updateCardAndMakeAnalytics(warehouseCardDTO, documentUnitDTO, documentDTO);
            }
        }
        if(documentDTO.isReverse()){
            documentDTO.setStatus(Document.Status.REVERSED.toString());
        }else{
            documentDTO.setStatus(Document.Status.BOOKED.toString());
        }

        documentDTO.setBookingDate(new Date());
        documentDTO = documentService.update(id, documentDTO);
        return documentDTO;
    }

    //Method for creating card with first analytic which will be represented as RECEIPT
    private WarehouseCardDTO makeCardAndAnalytics(WarehouseCardDTO warehouseCardDTO, DocumentUnitDTO documentUnitDTO, DocumentDTO documentDTO){
        if(documentDTO.getDocumentType().equals(Document.DocumentType.DISPATCH.toString())
                || documentDTO.getDocumentType().equals(Document.DocumentType.INTER_WAREHOUSE_TRAFFIC.toString())){
            throw new BadRequestException();
        } else if(documentDTO.isReverse()){
            throw new BadRequestException(); //Can't make storing if there's no card and analytics
        }

        warehouseCardDTO = new WarehouseCardDTO();
        WarehouseCardAnalyticsDTO warehouseCardAnalyticsDTO = new WarehouseCardAnalyticsDTO();

        //Card initialization
        warehouseCardDTO.setBusinessYear(documentDTO.getBusinessYear());

        if(documentDTO.getDocumentType().equals(Document.DocumentType.INTER_WAREHOUSE_TRAFFIC.toString())){
            warehouseCardDTO.setWarehouse(documentDTO.getInnerWarehouse());
        }else {
            warehouseCardDTO.setWarehouse(documentDTO.getWarehouse());
        }

        warehouseCardDTO.setWare(documentUnitDTO.getWare());
        warehouseCardDTO.setQuantityEntryTraffic(documentUnitDTO.getQuantity());
        warehouseCardDTO.setValueEntryTraffic(documentUnitDTO.getValue());
        warehouseCardDTO.setTotalValue(documentUnitDTO.getValue());
        warehouseCardDTO.setTotalQuantity(documentUnitDTO.getQuantity());
        warehouseCardDTO.setAveragePrice(documentUnitDTO.getPrice());
        //Save card
        warehouseCardDTO = warehouseCardService.create(warehouseCardDTO);

        //Analytics initialization
        warehouseCardAnalyticsDTO.setWarehouseCard(warehouseCardDTO);
        warehouseCardAnalyticsDTO.setTrafficType(WarehouseCardAnalytics.TrafficType.RECEIPT.toString());
        warehouseCardAnalyticsDTO.setDirection(WarehouseCardAnalytics.Direction.INCOMING.toString());
        warehouseCardAnalyticsDTO.setAveragePrice(documentUnitDTO.getPrice());
        warehouseCardAnalyticsDTO.setQuantity(documentUnitDTO.getQuantity());
        warehouseCardAnalyticsDTO.setValue(documentUnitDTO.getValue());
        //Save analytics
        warehouseCardAnalyticsDTO = warehouseCardAnalyticsService.create(warehouseCardAnalyticsDTO);

        return warehouseCardDTO;
    }

    //Method for updating existing card and creating new analytics for that card
    private WarehouseCardDTO updateCardAndMakeAnalytics(WarehouseCardDTO warehouseCardDTO, DocumentUnitDTO documentUnitDTO, DocumentDTO documentDTO){
        WarehouseCardAnalyticsDTO warehouseCardAnalyticsDTO = new WarehouseCardAnalyticsDTO();
        warehouseCardAnalyticsDTO.setWarehouseCard(warehouseCardDTO);

        Document.DocumentType documentType = Document.DocumentType.valueOf(documentDTO.getDocumentType());
        boolean reverse = documentDTO.isReverse();

        if(reverse){
            if(documentDTO.getStatus().equals(Document.Status.BOOKED.toString())){
                documentUnitDTO.setQuantity(documentUnitDTO.getQuantity()*(-1));
                documentUnitDTO.setValue(documentUnitDTO.getValue()*(-1));
            }else {
                throw new BadRequestException();
            }
        }

        switch (documentType){
            case RECEIPT:{
                if(Math.abs(documentUnitDTO.getQuantity()) > warehouseCardDTO.getTotalQuantity() && reverse){
                    throw new BadRequestException();
                }
                warehouseCardAnalyticsDTO = bookReceipt(warehouseCardAnalyticsDTO, documentUnitDTO, reverse);
                warehouseCardDTO = updateCardReceipt(warehouseCardDTO, documentUnitDTO);
                warehouseCardDTO = updateAveragePrice(warehouseCardDTO, documentUnitDTO); //only receipt changes price
                warehouseCardDTO = updateTotalQuantity(warehouseCardDTO);
                warehouseCardDTO = updateTotalValue(warehouseCardDTO);
                break;
            }
            case DISPATCH:{
                if(Math.abs(documentUnitDTO.getQuantity()) > warehouseCardDTO.getTotalQuantity()){
                    throw new BadRequestException();
                }

                warehouseCardAnalyticsDTO = bookDispatch(warehouseCardAnalyticsDTO, documentUnitDTO, reverse);
                warehouseCardDTO = updateCardDispatch(warehouseCardDTO, documentUnitDTO);
                warehouseCardDTO = updateTotalQuantity(warehouseCardDTO);
                warehouseCardDTO = updateTotalValue(warehouseCardDTO);
                break;
            }
            case INTER_WAREHOUSE_TRAFFIC:{
                //Better solution for this -> control on UI somehow? Check for store here.
                if(Math.abs(documentUnitDTO.getQuantity()) > warehouseCardDTO.getTotalQuantity()){
                    throw new BadRequestException();
                }
                warehouseCardAnalyticsDTO = bookDispatch(warehouseCardAnalyticsDTO, documentUnitDTO, reverse);
                warehouseCardDTO = updateCardDispatch(warehouseCardDTO, documentUnitDTO);
                warehouseCardDTO = updateTotalQuantity(warehouseCardDTO);
                warehouseCardDTO = updateTotalValue(warehouseCardDTO);

                //Inner warehouse card possible doesn't exist create it like RECEIPT, or update it with analytics like RECEIPT
                WarehouseCardDTO innerWarehouseCardDTO = warehouseCardService.read(documentUnitDTO.getWare(),documentDTO.getBusinessYear(), documentDTO.getInnerWarehouse());
                if(innerWarehouseCardDTO == null){
                    makeCardAndAnalytics(innerWarehouseCardDTO, documentUnitDTO, documentDTO);
                }else {
                    WarehouseCardAnalyticsDTO innerWarehouseCardAnalyticsDTO = new WarehouseCardAnalyticsDTO();
                    innerWarehouseCardAnalyticsDTO.setWarehouseCard(innerWarehouseCardDTO);
                    innerWarehouseCardAnalyticsDTO = bookReceipt(innerWarehouseCardAnalyticsDTO, documentUnitDTO, reverse);
                    innerWarehouseCardDTO = updateCardReceipt(innerWarehouseCardDTO, documentUnitDTO);
                    innerWarehouseCardDTO = updateAveragePrice(innerWarehouseCardDTO, documentUnitDTO);
                    innerWarehouseCardDTO = updateTotalQuantity(innerWarehouseCardDTO);
                    innerWarehouseCardDTO = updateTotalValue(innerWarehouseCardDTO);

                    //Save and update card and analytics for inter-warehouse traffic
                    innerWarehouseCardAnalyticsDTO = warehouseCardAnalyticsService.create(innerWarehouseCardAnalyticsDTO);
                    innerWarehouseCardDTO = warehouseCardService.update(innerWarehouseCardDTO.getId(), innerWarehouseCardDTO);
                }
                break;
            }
        }
        warehouseCardAnalyticsDTO = warehouseCardAnalyticsService.create(warehouseCardAnalyticsDTO);
        warehouseCardDTO = warehouseCardService.update(warehouseCardDTO.getId(), warehouseCardDTO);
        return warehouseCardDTO;
    }


    private WarehouseCardAnalyticsDTO bookReceipt(WarehouseCardAnalyticsDTO analyticsDTO, DocumentUnitDTO documentUnitDTO, boolean reverse){
        analyticsDTO.setTrafficType(WarehouseCardAnalytics.TrafficType.RECEIPT.toString());
        if(reverse){
            analyticsDTO.setDirection(WarehouseCardAnalytics.Direction.OUTGOING.toString());
        }else {
            analyticsDTO.setDirection(WarehouseCardAnalytics.Direction.INCOMING.toString());
        }
        analyticsDTO.setQuantity(documentUnitDTO.getQuantity());
        analyticsDTO.setAveragePrice(documentUnitDTO.getPrice());
        analyticsDTO.setValue(documentUnitDTO.getValue());
        return analyticsDTO;
    }

    private WarehouseCardAnalyticsDTO bookDispatch(WarehouseCardAnalyticsDTO analyticsDTO, DocumentUnitDTO documentUnitDTO, boolean reverse){
        analyticsDTO.setTrafficType(WarehouseCardAnalytics.TrafficType.DISPATCH.toString());
        if(reverse){
            analyticsDTO.setDirection(WarehouseCardAnalytics.Direction.INCOMING.toString());
        }else {
            analyticsDTO.setDirection(WarehouseCardAnalytics.Direction.OUTGOING.toString());
        }
        analyticsDTO.setQuantity(documentUnitDTO.getQuantity());
        analyticsDTO.setAveragePrice(documentUnitDTO.getPrice());
        analyticsDTO.setValue(documentUnitDTO.getValue());
        return analyticsDTO;
    }

    private WarehouseCardDTO updateCardReceipt(WarehouseCardDTO warehouseCardDTO, DocumentUnitDTO documentUnitDTO){
        double quantityEntry = warehouseCardDTO.getQuantityEntryTraffic();
        double valueEntry = warehouseCardDTO.getValueEntryTraffic();

        quantityEntry = quantityEntry + documentUnitDTO.getQuantity();
        warehouseCardDTO.setQuantityEntryTraffic(quantityEntry);

        valueEntry = valueEntry + documentUnitDTO.getValue();
        warehouseCardDTO.setValueEntryTraffic(valueEntry);

        return warehouseCardDTO;
    }

    private WarehouseCardDTO updateCardDispatch(WarehouseCardDTO warehouseCardDTO, DocumentUnitDTO documentUnitDTO){
        double quantityExit = warehouseCardDTO.getQuantityExitTraffic();
        double valueExit = warehouseCardDTO.getValueExitTraffic();

        quantityExit = quantityExit + documentUnitDTO.getQuantity();
        warehouseCardDTO.setQuantityExitTraffic(quantityExit);

        valueExit = valueExit + documentUnitDTO.getValue();
        warehouseCardDTO.setValueExitTraffic(valueExit);

        return warehouseCardDTO;
    }

    private WarehouseCardDTO updateAveragePrice(WarehouseCardDTO warehouseCardDTO, DocumentUnitDTO documentUnitDTO){
        double totalValue = warehouseCardDTO.getTotalValue();
        double entryQuantity = documentUnitDTO.getQuantity();
        double price = documentUnitDTO.getPrice();
        double totalQuantity = warehouseCardDTO.getTotalQuantity();


        double newAveragePrice = (totalValue + entryQuantity * price) / (totalQuantity + entryQuantity);
        if(Double.isNaN(newAveragePrice)){
            newAveragePrice = 0.0;
        }

        warehouseCardDTO.setAveragePrice(newAveragePrice);

        return warehouseCardDTO;
    }

    private WarehouseCardDTO updateTotalQuantity(WarehouseCardDTO warehouseCardDTO){
        double totalQuantity = 0.0;
        double initQuantity = warehouseCardDTO.getInitialQuantity();
        double trafficEntry = warehouseCardDTO.getQuantityEntryTraffic();
        double trafficExit = warehouseCardDTO.getQuantityExitTraffic();

        totalQuantity = initQuantity + trafficEntry - trafficExit;
        warehouseCardDTO.setTotalQuantity(totalQuantity);

        return warehouseCardDTO;
    }

    private WarehouseCardDTO updateTotalValue(WarehouseCardDTO warehouseCardDTO){
        double totalValue = 0.0;
        double initValue = warehouseCardDTO.getInitialValue();
        double trafficEntry = warehouseCardDTO.getValueEntryTraffic();
        double trafficExit = warehouseCardDTO.getValueExitTraffic();

        totalValue = initValue + trafficEntry - trafficExit;
        warehouseCardDTO.setTotalValue(totalValue);

        return warehouseCardDTO;
    }
}
