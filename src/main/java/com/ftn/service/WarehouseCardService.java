package com.ftn.service;

import com.ftn.model.BusinessYear;
import com.ftn.model.Ware;
import com.ftn.model.Warehouse;
import com.ftn.model.WarehouseCard;
import com.ftn.model.dto.ReportDataDTO;

import java.util.List;

/**
 * Created by JELENA on 31.5.2017.
 */
public interface WarehouseCardService {

    List<WarehouseCard> read();

    //read warehouse cards for active warehouse
    List<WarehouseCard> read(Long id);

    //Check if there's card for ware, year and specific warehouse
    WarehouseCard read(Ware ware, BusinessYear businessYear, Warehouse warehouse);

    WarehouseCard create(WarehouseCard warehouseCard);

    WarehouseCard update(Long id, WarehouseCard warehouseCard);

    void delete(Long id);

    String generateReport(ReportDataDTO reportDataDTO);

    WarehouseCard getWarehouseCardForWare(Ware ware);
}
