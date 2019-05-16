package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.BusinessPartner;
import com.ftn.repository.BusinessPartnerDao;
import com.ftn.service.BusinessPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<BusinessPartner> read() {
        return businessPartnerDao.findAll();
    }

    @Override
    public BusinessPartner create(BusinessPartner businessPartner) {
        if (businessPartnerDao.findById(businessPartner.getId()).isPresent())
            throw new BadRequestException();

        return businessPartnerDao.save(businessPartner);
    }

    @Override
    public BusinessPartner update(Long id, BusinessPartner businessPartner) {
        businessPartnerDao.findById(id).orElseThrow(NotFoundException::new);
        return businessPartnerDao.save(businessPartner);
    }

    @Override
    public void delete(Long id) {
        final BusinessPartner businessPartner = businessPartnerDao.findById(id).orElseThrow(NotFoundException::new);
        businessPartnerDao.delete(businessPartner);
    }
}
