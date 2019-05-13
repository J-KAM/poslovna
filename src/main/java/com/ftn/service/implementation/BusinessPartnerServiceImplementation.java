package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.BusinessPartner;
import com.ftn.model.dto.BusinessPartnerDTO;
import com.ftn.repository.BusinessPartnerDao;
import com.ftn.service.BusinessPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 21.5.2017..
 */
@Service
public class BusinessPartnerServiceImplementation implements BusinessPartnerService {

    private final BusinessPartnerDao businessPartnerDao;

    @Autowired
    public BusinessPartnerServiceImplementation(BusinessPartnerDao businessPartnerDao) {
        this.businessPartnerDao = businessPartnerDao;
    }

    @Override
    public List<BusinessPartnerDTO> read() {
        return businessPartnerDao.findAll().stream().map(BusinessPartnerDTO::new).collect(Collectors.toList());
    }

    @Override
    public BusinessPartnerDTO create(BusinessPartnerDTO businessPartnerDTO) {
        if (businessPartnerDao.findById(businessPartnerDTO.getId()).isPresent())
            throw new BadRequestException();

        final BusinessPartner businessPartner = businessPartnerDTO.construct();
        businessPartnerDao.save(businessPartner);
        return new BusinessPartnerDTO(businessPartner);
    }

    @Override
    public BusinessPartnerDTO update(Long id, BusinessPartnerDTO businessPartnerDTO) {
        final BusinessPartner businessPartner = businessPartnerDao.findById(id).orElseThrow(NotFoundException::new);
        businessPartner.merge(businessPartnerDTO);
        businessPartnerDao.save(businessPartner);
        return new BusinessPartnerDTO(businessPartner);
    }

    @Override
    public void delete(Long id) {
        final BusinessPartner businessPartner = businessPartnerDao.findById(id).orElseThrow(NotFoundException::new);
        businessPartnerDao.delete(businessPartner);
    }
}
