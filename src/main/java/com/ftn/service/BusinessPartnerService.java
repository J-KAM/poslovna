package com.ftn.service;

import com.ftn.model.BusinessPartner;

import java.util.List;

/**
 * Created by Olivera on 21.5.2017..
 */
public interface BusinessPartnerService {

    List<BusinessPartner> read();

    BusinessPartner create(BusinessPartner businessPartner);

    BusinessPartner update(Long id, BusinessPartner businessPartner);

    void delete(Long id);
}
