package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Document;
import com.ftn.model.DocumentUnit;
import com.ftn.model.WarehouseCard;
import com.ftn.model.WarehouseCardAnalytics;
import com.ftn.model.enums.Direction;
import com.ftn.model.enums.DocumentType;
import com.ftn.model.enums.Status;
import com.ftn.model.enums.TrafficType;
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
        document.setId(id);

        if(documentUnits.size() == 0){
            throw new BadRequestException();
        }

        //Check if year is active
        boolean active = businessYearService.active(document.getBusinessYear());
        if(!active){
            throw new BadRequestException();
        }

        for(DocumentUnit documentUnit : documentUnits){
            WarehouseCard warehouseCard = warehouseCardService.read(documentUnit.getWare(),document.getBusinessYear(), document.getWarehouse());
            if(warehouseCard == null){
                warehouseCard = makeCardAndAnalytics(warehouseCard, documentUnit, document);
            }else {
                warehouseCard = updateCardAndMakeAnalytics(warehouseCard, documentUnit, document);
            }
        }
        if(document.isReverse()){
            document.setStatus(Status.REVERSED);
        }else{
            document.setStatus(Status.BOOKED);
        }

        document.setBookingDate(new Date());
        document = documentService.update(id, document);
        return document;
    }

    //Method for creating card with first analytic which will be represented as RECEIPT
    private WarehouseCard makeCardAndAnalytics(WarehouseCard warehouseCard, DocumentUnit documentUnit, Document document){
        if(document.getDocumentType().equals(DocumentType.DISPATCH.toString())
                || document.getDocumentType().equals(DocumentType.INTER_WAREHOUSE_TRAFFIC.toString())){
            throw new BadRequestException();
        } else if(document.isReverse()){
            throw new BadRequestException(); //Can't make storing if there's no card and analytics
        }

        warehouseCard = new WarehouseCard();
        WarehouseCardAnalytics warehouseCardAnalytics = new WarehouseCardAnalytics();

        //Card initialization
        warehouseCard.setBusinessYear(document.getBusinessYear());

        if(document.getDocumentType().equals(DocumentType.INTER_WAREHOUSE_TRAFFIC)){
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
        warehouseCardAnalytics.setTrafficType(TrafficType.RECEIPT);
        warehouseCardAnalytics.setDirection(Direction.INCOMING);
        warehouseCardAnalytics.setAveragePrice(documentUnit.getPrice());
        warehouseCardAnalytics.setQuantity(documentUnit.getQuantity());
        warehouseCardAnalytics.setValue(documentUnit.getValue());
        //Save analytics
        warehouseCardAnalytics = warehouseCardAnalyticsService.create(warehouseCardAnalytics);

        return warehouseCard;
    }

    //Method for updating existing card and creating new analytics for that card
    private WarehouseCard updateCardAndMakeAnalytics(WarehouseCard warehouseCard, DocumentUnit documentUnit, Document document){
        WarehouseCardAnalytics warehouseCardAnalytics = new WarehouseCardAnalytics();
        warehouseCardAnalytics.setWarehouseCard(warehouseCard);

        DocumentType documentType = document.getDocumentType();
        boolean reverse = document.isReverse();

        if(reverse){
            if(document.getStatus().equals(Status.BOOKED)){
                documentUnit.setQuantity(documentUnit.getQuantity()*(-1));
                documentUnit.setValue(documentUnit.getValue()*(-1));
            }else {
                throw new BadRequestException();
            }
        }

        switch (documentType){
            case RECEIPT:{
                if(Math.abs(documentUnit.getQuantity()) > warehouseCard.getTotalQuantity() && reverse){
                    throw new BadRequestException();
                }
                warehouseCardAnalytics = bookReceipt(warehouseCardAnalytics, documentUnit, reverse);
                warehouseCard = updateCardReceipt(warehouseCard, documentUnit);
                warehouseCard = updateAveragePrice(warehouseCard, documentUnit); //only receipt changes price
                warehouseCard = updateTotalQuantity(warehouseCard);
                warehouseCard = updateTotalValue(warehouseCard);
                break;
            }
            case DISPATCH:{
                if(Math.abs(documentUnit.getQuantity()) > warehouseCard.getTotalQuantity()){
                    throw new BadRequestException();
                }

                warehouseCardAnalytics = bookDispatch(warehouseCardAnalytics, documentUnit, reverse);
                warehouseCard = updateCardDispatch(warehouseCard, documentUnit);
                warehouseCard = updateTotalQuantity(warehouseCard);
                warehouseCard = updateTotalValue(warehouseCard);
                break;
            }
            case INTER_WAREHOUSE_TRAFFIC:{
                //Better solution for this -> control on UI somehow? Check for store here.
                if(Math.abs(documentUnit.getQuantity()) > warehouseCard.getTotalQuantity()){
                    throw new BadRequestException();
                }
                warehouseCardAnalytics = bookDispatch(warehouseCardAnalytics, documentUnit, reverse);
                warehouseCard = updateCardDispatch(warehouseCard, documentUnit);
                warehouseCard = updateTotalQuantity(warehouseCard);
                warehouseCard = updateTotalValue(warehouseCard);

                //Inner warehouse card possible doesn't exist create it like RECEIPT, or update it with analytics like RECEIPT
                WarehouseCard innerWarehouseCard = warehouseCardService.read(documentUnit.getWare(),document.getBusinessYear(), document.getInnerWarehouse());
                if(innerWarehouseCard == null){
                    makeCardAndAnalytics(innerWarehouseCard, documentUnit, document);
                }else {
                    WarehouseCardAnalytics innerWarehouseCardAnalytics = new WarehouseCardAnalytics();
                    innerWarehouseCardAnalytics.setWarehouseCard(innerWarehouseCard);
                    innerWarehouseCardAnalytics = bookReceipt(innerWarehouseCardAnalytics, documentUnit, reverse);
                    innerWarehouseCard = updateCardReceipt(innerWarehouseCard, documentUnit);
                    innerWarehouseCard = updateAveragePrice(innerWarehouseCard, documentUnit);
                    innerWarehouseCard = updateTotalQuantity(innerWarehouseCard);
                    innerWarehouseCard = updateTotalValue(innerWarehouseCard);

                    //Save and update card and analytics for inter-warehouse traffic
                    innerWarehouseCardAnalytics = warehouseCardAnalyticsService.create(innerWarehouseCardAnalytics);
                    innerWarehouseCard = warehouseCardService.update(innerWarehouseCard.getId(), innerWarehouseCard);
                }
                break;
            }
        }
        warehouseCardAnalytics = warehouseCardAnalyticsService.create(warehouseCardAnalytics);
        warehouseCard = warehouseCardService.update(warehouseCard.getId(), warehouseCard);
        return warehouseCard;
    }


    private WarehouseCardAnalytics bookReceipt(WarehouseCardAnalytics analytics, DocumentUnit documentUnit, boolean reverse){
        analytics.setTrafficType(TrafficType.RECEIPT);
        if(reverse){
            analytics.setDirection(Direction.OUTGOING);
        }else {
            analytics.setDirection(Direction.INCOMING);
        }
        analytics.setQuantity(documentUnit.getQuantity());
        analytics.setAveragePrice(documentUnit.getPrice());
        analytics.setValue(documentUnit.getValue());
        return analytics;
    }

    private WarehouseCardAnalytics bookDispatch(WarehouseCardAnalytics analytics, DocumentUnit documentUnit, boolean reverse){
        analytics.setTrafficType(TrafficType.DISPATCH);
        if(reverse){
            analytics.setDirection(Direction.INCOMING);
        }else {
            analytics.setDirection(Direction.OUTGOING);
        }
        analytics.setQuantity(documentUnit.getQuantity());
        analytics.setAveragePrice(documentUnit.getPrice());
        analytics.setValue(documentUnit.getValue());
        return analytics;
    }

    private WarehouseCard updateCardReceipt(WarehouseCard warehouseCard, DocumentUnit documentUnit){
        double quantityEntry = warehouseCard.getQuantityEntryTraffic();
        double valueEntry = warehouseCard.getValueEntryTraffic();

        quantityEntry = quantityEntry + documentUnit.getQuantity();
        warehouseCard.setQuantityEntryTraffic(quantityEntry);

        valueEntry = valueEntry + documentUnit.getValue();
        warehouseCard.setValueEntryTraffic(valueEntry);

        return warehouseCard;
    }

    private WarehouseCard updateCardDispatch(WarehouseCard warehouseCard, DocumentUnit documentUnit){
        double quantityExit = warehouseCard.getQuantityExitTraffic();
        double valueExit = warehouseCard.getValueExitTraffic();

        quantityExit = quantityExit + documentUnit.getQuantity();
        warehouseCard.setQuantityExitTraffic(quantityExit);

        valueExit = valueExit + documentUnit.getValue();
        warehouseCard.setValueExitTraffic(valueExit);

        return warehouseCard;
    }

    private WarehouseCard updateAveragePrice(WarehouseCard warehouseCard, DocumentUnit documentUnit){
        double totalValue = warehouseCard.getTotalValue();
        double entryQuantity = documentUnit.getQuantity();
        double price = documentUnit.getPrice();
        double totalQuantity = warehouseCard.getTotalQuantity();


        double newAveragePrice = (totalValue + entryQuantity * price) / (totalQuantity + entryQuantity);
        if(Double.isNaN(newAveragePrice)){
            newAveragePrice = 0.0;
        }

        warehouseCard.setAveragePrice(newAveragePrice);

        return warehouseCard;
    }

    private WarehouseCard updateTotalQuantity(WarehouseCard warehouseCard){
        double totalQuantity = 0.0;
        double initQuantity = warehouseCard.getInitialQuantity();
        double trafficEntry = warehouseCard.getQuantityEntryTraffic();
        double trafficExit = warehouseCard.getQuantityExitTraffic();

        totalQuantity = initQuantity + trafficEntry - trafficExit;
        warehouseCard.setTotalQuantity(totalQuantity);

        return warehouseCard;
    }

    private WarehouseCard updateTotalValue(WarehouseCard warehouseCard){
        double totalValue = 0.0;
        double initValue = warehouseCard.getInitialValue();
        double trafficEntry = warehouseCard.getValueEntryTraffic();
        double trafficExit = warehouseCard.getValueExitTraffic();

        totalValue = initValue + trafficEntry - trafficExit;
        warehouseCard.setTotalValue(totalValue);

        return warehouseCard;
    }
}
