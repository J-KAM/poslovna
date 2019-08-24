package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Company;
import com.ftn.model.User;
import com.ftn.model.Ware;
import com.ftn.repository.CompanyDao;
import com.ftn.repository.WareDao;
import com.ftn.service.AuthenticationService;
import com.ftn.service.WareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Ware> read() {
        final User user = authenticationService.getCurrentUser();
        if (User.Role.EMPLOYEE.equals(user.getRole())) {
            final Company company = user.getCompany();
            return wareDao.findByWareGroupCompanyId(company.getId());
        } else {
            return wareDao.findAll();
        }
    }

    @Override
    public Ware create(Ware ware) {
        if (wareDao.findById(ware.getId()).isPresent()) {
            throw new BadRequestException();
        }
        return wareDao.save(ware);
    }

    @Override
    public Ware update(Long id, Ware ware) {
        final Ware persistentWare = getWare(id);
        ware.setId(persistentWare.getId());
        return wareDao.save(ware);
    }

    @Override
    public void delete(Long id) {
        final Ware ware = getWare(id);
        wareDao.delete(ware);
    }

    private Ware getWare(Long id) {
        final User user = authenticationService.getCurrentUser();
        if (User.Role.EMPLOYEE.equals(user.getRole())) {
            final Company company = user.getCompany();
            return wareDao.findByIdAndWareGroupCompanyId(id, company.getId()).orElseThrow(NotFoundException::new);
        } else {
            return wareDao.findById(id).orElseThrow(NotFoundException::new);
        }
    }
}
