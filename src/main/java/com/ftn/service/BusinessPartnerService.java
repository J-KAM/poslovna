package com.ftn.service;

import com.ftn.model.BusinessPartner;
import com.ftn.model.Warehouse;

import java.util.List;

/**
 * Created by Olivera on 21.5.2017..
 */
public interface BusinessPartnerService {

    List<BusinessPartner> read();

    List<BusinessPartner> readByCompany(Long id);

    BusinessPartner create(BusinessPartner businessPartner);

    BusinessPartner update(Long id, BusinessPartner businessPartner);

    void delete(Long id);
}
