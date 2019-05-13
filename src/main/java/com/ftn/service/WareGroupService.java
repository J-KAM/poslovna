package com.ftn.service;

import com.ftn.model.dto.WareGroupDTO;

import java.util.List;

/**
 * Created by Jasmina on 22/05/2017.
 */
public interface WareGroupService {

    List<WareGroupDTO> read();

    WareGroupDTO create(WareGroupDTO wareGroupDTO);

    WareGroupDTO update(Long id, WareGroupDTO wareGroupDTO);

    void delete(Long id);
}
