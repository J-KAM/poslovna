package com.ftn.service;

import com.ftn.model.dto.WareDTO;

import java.util.List;

/**
 * Created by JELENA on 30.5.2017.
 */
public interface WareService {

    List<WareDTO> read();

    WareDTO create(WareDTO wareDTO);

    WareDTO update(Long id, WareDTO wareDTO);

    void delete(Long id);
}
