package com.ftn.service;

import com.ftn.model.Ware;

import java.util.List;

/**
 * Created by JELENA on 30.5.2017.
 */
public interface WareService {

    List<Ware> read();

    Ware create(Ware ware);

    Ware update(Long id, Ware ware);

    void delete(Long id);
}
