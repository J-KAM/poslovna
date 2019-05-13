package com.ftn.service;

import com.ftn.model.dto.BusinessPartnerDTO;

import java.util.List;

/**
 * Created by Olivera on 21.5.2017..
 */
public interface BusinessPartnerService {

    List<BusinessPartnerDTO> read();

    BusinessPartnerDTO create(BusinessPartnerDTO businessPartnerDTO);

    BusinessPartnerDTO update(Long id, BusinessPartnerDTO businessPartnerDTO);

    void delete(Long id);
}
