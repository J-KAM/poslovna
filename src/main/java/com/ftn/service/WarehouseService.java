package com.ftn.service;

import com.ftn.model.Warehouse;

import java.util.List;

/**
 * Created by Olivera on 30.5.2017..
 */
public interface WarehouseService {

    List<Warehouse> read();

    List<Warehouse> readByCompany(Long id);

    Warehouse create(Warehouse warehouse);

    Warehouse update(Long id, Warehouse warehouse);

    void delete(Long id);

    String generateReport(Warehouse warehouse);
}
