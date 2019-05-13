package com.ftn.service;

import com.ftn.model.dto.BusinessYearDTO;

import java.util.List;

/**
 * Created by JELENA on 30.5.2017.
 */
public interface BusinessYearService {

    List<BusinessYearDTO> read();

    BusinessYearDTO create(BusinessYearDTO businessYearDTO);

    BusinessYearDTO update(Long id, BusinessYearDTO businessYearDTO);

    void delete(Long id);

    boolean active(BusinessYearDTO businessYearDTO);
}
