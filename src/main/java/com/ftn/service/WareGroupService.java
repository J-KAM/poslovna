package com.ftn.service;

import com.ftn.model.WareGroup;

import java.util.List;

/**
 * Created by Jasmina on 22/05/2017.
 */
public interface WareGroupService {

    List<WareGroup> read();

    WareGroup create(WareGroup wareGroup);

    WareGroup update(Long id, WareGroup wareGroup);

    void delete(Long id);
}
