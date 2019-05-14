package com.ftn.service;

import com.ftn.model.BusinessYear;
import com.ftn.model.WarehouseCard;
import com.ftn.model.dto.WareDTO;
import com.ftn.model.dto.WarehouseCardDTO;
import com.ftn.model.dto.WarehouseDTO;
import com.ftn.model.dto.*;


import java.util.List;

/**
 * Created by JELENA on 31.5.2017.
 */
public interface WarehouseCardService {

    List<WarehouseCardDTO> read();

    //read warehouse cards for active warehouse
    List<WarehouseCardDTO> read(Long id);

    //Check if there's card for ware, year and specific warehouse
    WarehouseCardDTO read(WareDTO wareDTO, BusinessYear businessYear, WarehouseDTO warehouseDTO);

    WarehouseCard create(WarehouseCard warehouseCard);

    WarehouseCardDTO update(Long id, WarehouseCardDTO warehouseCardDTO);

    void delete(Long id);

    String generateReport(ReportDataDTO reportDataDTO);

    WarehouseCardDTO getWarehouseCardForWare(WareDTO wareDTO);
}
