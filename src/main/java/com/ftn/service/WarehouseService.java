package com.ftn.service;

import com.ftn.model.Ware;
import com.ftn.model.dto.WarehouseCardDTO;
import com.ftn.model.dto.WarehouseDTO;

import java.util.List;

/**
 * Created by Olivera on 30.5.2017..
 */
public interface WarehouseService {

    List<WarehouseDTO> read();

    WarehouseDTO create(WarehouseDTO warehouseDTO);

    WarehouseDTO update(Long id, WarehouseDTO warehouseDTO);

    void delete(Long id);

    String generateReport(WarehouseDTO warehouseDTO);
}
