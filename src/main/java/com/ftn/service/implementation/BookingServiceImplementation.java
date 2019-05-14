package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Document;
import com.ftn.model.DocumentUnit;
import com.ftn.model.WarehouseCard;
import com.ftn.model.WarehouseCardAnalytics;
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
    public Document book(Long id, Document document) {
        List<DocumentUnit> documentUnits = documentUnitService.read(id);

        if(documentUnits.size() == 0){
            throw new BadRequestException();
        }

        //Check if year is active
        boolean active = businessYearService.active(document.getBusinessYear());
        if(!active){
            throw new BadRequestException();
        }

       /* for(DocumentUnit documentUnit : documentUnits){
            WarehouseCardDTO warehouseCardDTO = warehouseCardService.read(documentUnit.getWare(),document.getBusinessYear(), document.getWarehouse());
            if(warehouseCardDTO == null){
                warehouseCardDTO = makeCardAndAnalytics(warehouseCardDTO, documentUnit, document);
            }else {
                warehouseCardDTO = updateCardAndMakeAnalytics(warehouseCardDTO, documentUnit, document);
            }
        }*/
        if(document.isReverse()){
            document.setStatus(Document.Status.REVERSED);
        }else{
            document.setStatus(Document.Status.BOOKED);
        }

        document.setBookingDate(new Date());
        document = documentService.update(id, document);
        return document;
    }

    //Method for creating card with first analytic which will be represented as RECEIPT
    private WarehouseCard makeCardAndAnalytics(WarehouseCard warehouseCard, DocumentUnit documentUnit, Document document){
        if(document.getDocumentType().equals(Document.DocumentType.DISPATCH.toString())
                || document.getDocumentType().equals(Document.DocumentType.INTER_WAREHOUSE_TRAFFIC.toString())){
            throw new BadRequestException();
        } else if(document.isReverse()){
            throw new BadRequestException(); //Can't make storing if there's no card and analytics
        }

        warehouseCard = new WarehouseCard();
        WarehouseCardAnalytics warehouseCardAnalytics = new WarehouseCardAnalytics();

        //Card initialization
        warehouseCard.setBusinessYear(document.getBusinessYear());

        if(document.getDocumentType().equals(Document.DocumentType.INTER_WAREHOUSE_TRAFFIC)){
            warehouseCard.setWarehouse(document.getInnerWarehouse());
        }else {
            warehouseCard.setWarehouse(document.getWarehouse());
        }

        warehouseCard.setWare(documentUnit.getWare());
        warehouseCard.setQuantityEntryTraffic(documentUnit.getQuantity());
        warehouseCard.setValueEntryTraffic(documentUnit.getValue());
        warehouseCard.setTotalValue(documentUnit.getValue());
        warehouseCard.setTotalQuantity(documentUnit.getQuantity());
        warehouseCard.setAveragePrice(documentUnit.getPrice());
        //Save card
        warehouseCard = warehouseCardService.create(warehouseCard);

        //Analytics initialization
        warehouseCardAnalytics.setWarehouseCard(warehouseCard);
        warehouseCardAnalytics.setTrafficType(WarehouseCardAnalytics.TrafficType.RECEIPT);
        warehouseCardAnalytics.setDirection(WarehouseCardAnalytics.Direction.INCOMING);
        warehouseCardAnalytics.setAveragePrice(documentUnit.getPrice());
        warehouseCardAnalytics.setQuantity(documentUnit.getQuantity());
        warehouseCardAnalytics.setValue(documentUnit.getValue());
        //Save analytics
        warehouseCardAnalytics = warehouseCardAnalyticsService.create(warehouseCardAnalytics);

        return warehouseCard;
    }

    //Method for updating existing card and creating new analytics for that card
    private WarehouseCardDTO updateCardAndMakeAnalytics(WarehouseCardDTO warehouseCardDTO, DocumentUnit documentUnit, Document document){
        WarehouseCardAnalyticsDTO warehouseCardAnalyticsDTO = new WarehouseCardAnalyticsDTO();
       /* warehouseCardAnalyticsDTO.setWarehouseCard(warehouseCardDTO);

        Document.DocumentType documentType = document.getDocumentType();
        boolean reverse = document.isReverse();

        if(reverse){
            if(document.getStatus().equals(Document.Status.BOOKED)){
                documentUnit.setQuantity(documentUnit.getQuantity()*(-1));
                documentUnit.setValue(documentUnit.getValue()*(-1));
            }else {
                throw new BadRequestException();
            }
        }

        switch (documentType){
            case RECEIPT:{
                if(Math.abs(documentUnit.getQuantity()) > warehouseCardDTO.getTotalQuantity() && reverse){
                    throw new BadRequestException();
                }
                warehouseCardAnalyticsDTO = bookReceipt(warehouseCardAnalyticsDTO, documentUnit, reverse);
                warehouseCardDTO = updateCardReceipt(warehouseCardDTO, documentUnit);
                warehouseCardDTO = updateAveragePrice(warehouseCardDTO, documentUnit); //only receipt changes price
                warehouseCardDTO = updateTotalQuantity(warehouseCardDTO);
                warehouseCardDTO = updateTotalValue(warehouseCardDTO);
                break;
            }
            case DISPATCH:{
                if(Math.abs(documentUnit.getQuantity()) > warehouseCardDTO.getTotalQuantity()){
                    throw new BadRequestException();
                }

                warehouseCardAnalyticsDTO = bookDispatch(warehouseCardAnalyticsDTO, documentUnit, reverse);
                warehouseCardDTO = updateCardDispatch(warehouseCardDTO, documentUnit);
                warehouseCardDTO = updateTotalQuantity(warehouseCardDTO);
                warehouseCardDTO = updateTotalValue(warehouseCardDTO);
                break;
            }
            case INTER_WAREHOUSE_TRAFFIC:{
                //Better solution for this -> control on UI somehow? Check for store here.
                if(Math.abs(documentUnit.getQuantity()) > warehouseCardDTO.getTotalQuantity()){
                    throw new BadRequestException();
                }
                warehouseCardAnalyticsDTO = bookDispatch(warehouseCardAnalyticsDTO, documentUnit, reverse);
                warehouseCardDTO = updateCardDispatch(warehouseCardDTO, documentUnit);
                warehouseCardDTO = updateTotalQuantity(warehouseCardDTO);
                warehouseCardDTO = updateTotalValue(warehouseCardDTO);

                //Inner warehouse card possible doesn't exist create it like RECEIPT, or update it with analytics like RECEIPT
                WarehouseCardDTO innerWarehouseCardDTO = warehouseCardService.read(documentUnit.getWare(),document.getBusinessYear(), document.getInnerWarehouse());
                if(innerWarehouseCardDTO == null){
                    makeCardAndAnalytics(innerWarehouseCardDTO, documentUnit, document);
                }else {
                    WarehouseCardAnalyticsDTO innerWarehouseCardAnalyticsDTO = new WarehouseCardAnalyticsDTO();
                    innerWarehouseCardAnalyticsDTO.setWarehouseCard(innerWarehouseCardDTO);
                    innerWarehouseCardAnalyticsDTO = bookReceipt(innerWarehouseCardAnalyticsDTO, documentUnit, reverse);
                    innerWarehouseCardDTO = updateCardReceipt(innerWarehouseCardDTO, documentUnit);
                    innerWarehouseCardDTO = updateAveragePrice(innerWarehouseCardDTO, documentUnit);
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
        warehouseCardDTO = warehouseCardService.update(warehouseCardDTO.getId(), warehouseCardDTO);*/
        return warehouseCardDTO;
    }


    private WarehouseCardAnalyticsDTO bookReceipt(WarehouseCardAnalyticsDTO analyticsDTO, DocumentUnit documentUnit, boolean reverse){
        analyticsDTO.setTrafficType(WarehouseCardAnalytics.TrafficType.RECEIPT.toString());
        if(reverse){
            analyticsDTO.setDirection(WarehouseCardAnalytics.Direction.OUTGOING.toString());
        }else {
            analyticsDTO.setDirection(WarehouseCardAnalytics.Direction.INCOMING.toString());
        }
        analyticsDTO.setQuantity(documentUnit.getQuantity());
        analyticsDTO.setAveragePrice(documentUnit.getPrice());
        analyticsDTO.setValue(documentUnit.getValue());
        return analyticsDTO;
    }

    private WarehouseCardAnalyticsDTO bookDispatch(WarehouseCardAnalyticsDTO analyticsDTO, DocumentUnit documentUnit, boolean reverse){
        analyticsDTO.setTrafficType(WarehouseCardAnalytics.TrafficType.DISPATCH.toString());
        if(reverse){
            analyticsDTO.setDirection(WarehouseCardAnalytics.Direction.INCOMING.toString());
        }else {
            analyticsDTO.setDirection(WarehouseCardAnalytics.Direction.OUTGOING.toString());
        }
        analyticsDTO.setQuantity(documentUnit.getQuantity());
        analyticsDTO.setAveragePrice(documentUnit.getPrice());
        analyticsDTO.setValue(documentUnit.getValue());
        return analyticsDTO;
    }

    private WarehouseCardDTO updateCardReceipt(WarehouseCardDTO warehouseCardDTO, DocumentUnit documentUnit){
        double quantityEntry = warehouseCardDTO.getQuantityEntryTraffic();
        double valueEntry = warehouseCardDTO.getValueEntryTraffic();

        quantityEntry = quantityEntry + documentUnit.getQuantity();
        warehouseCardDTO.setQuantityEntryTraffic(quantityEntry);

        valueEntry = valueEntry + documentUnit.getValue();
        warehouseCardDTO.setValueEntryTraffic(valueEntry);

        return warehouseCardDTO;
    }

    private WarehouseCardDTO updateCardDispatch(WarehouseCardDTO warehouseCardDTO, DocumentUnit documentUnit){
        double quantityExit = warehouseCardDTO.getQuantityExitTraffic();
        double valueExit = warehouseCardDTO.getValueExitTraffic();

        quantityExit = quantityExit + documentUnit.getQuantity();
        warehouseCardDTO.setQuantityExitTraffic(quantityExit);

        valueExit = valueExit + documentUnit.getValue();
        warehouseCardDTO.setValueExitTraffic(valueExit);

        return warehouseCardDTO;
    }

    private WarehouseCardDTO updateAveragePrice(WarehouseCardDTO warehouseCardDTO, DocumentUnit documentUnit){
        double totalValue = warehouseCardDTO.getTotalValue();
        double entryQuantity = documentUnit.getQuantity();
        double price = documentUnit.getPrice();
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
