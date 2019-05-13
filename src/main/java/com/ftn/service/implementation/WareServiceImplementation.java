package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.model.dto.WareDTO;
import com.ftn.repository.CompanyDao;
import com.ftn.repository.WareDao;
import com.ftn.service.AuthenticationService;
import com.ftn.service.WareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 30.5.2017.
 */
@Service
public class WareServiceImplementation implements WareService {

    private final WareDao wareDao;

    private final CompanyDao companyDao;

    private final AuthenticationService authenticationService;

    @Autowired
    public WareServiceImplementation(WareDao wareDao, CompanyDao companyDao,
             AuthenticationService authenticationService) {
        this.wareDao = wareDao;
        this.companyDao = companyDao;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<WareDTO> read() {
        final User user = authenticationService.getCurrentUser();
        if (user instanceof Employee) {
            final Company company = ((Employee) user).getCompany();
            return wareDao.findByWareGroupCompanyId(company.getId()).stream().map(WareDTO::new).collect(Collectors.toList());
        } else {
            return wareDao.findAll().stream().map(WareDTO::new).collect(Collectors.toList());
        }
    }

    @Override
    public WareDTO create(WareDTO wareDTO) {
        if (wareDao.findById(wareDTO.getId()).isPresent()) {
            throw new BadRequestException();
        }
        final Ware ware = wareDTO.construct();
        wareDao.save(ware);
        return new WareDTO(ware);
    }

    @Override
    public WareDTO update(Long id, WareDTO wareDTO) {
        final Ware ware = getWare(id);
        ware.merge(wareDTO);
        wareDao.save(ware);
        return new WareDTO(ware);
    }

    @Override
    public void delete(Long id) {
        final Ware ware = getWare(id);
        wareDao.delete(ware);
    }

    private Ware getWare(Long id) {
        final User user = authenticationService.getCurrentUser();
        if (user instanceof Employee) {
            final Company company = ((Employee) user).getCompany();
            return wareDao.findByIdAndWareGroupCompanyId(id, company.getId()).orElseThrow(NotFoundException::new);
        } else {
            return wareDao.findById(id).orElseThrow(NotFoundException::new);
        }
    }
}
