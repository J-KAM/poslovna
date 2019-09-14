package com.ftn.service;

import com.ftn.model.BusinessYear;

import java.util.List;

/**
 * Created by JELENA on 30.5.2017.
 */
public interface BusinessYearService {

    List<BusinessYear> read();

    BusinessYear read(Long id);

    BusinessYear create(BusinessYear businessYear);

    BusinessYear update(Long id, BusinessYear businessYear);

    void delete(Long id);

    boolean active(BusinessYear businessYear);
}
