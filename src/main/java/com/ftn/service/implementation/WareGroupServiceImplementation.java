package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Company;
import com.ftn.model.User;
import com.ftn.model.WareGroup;
import com.ftn.model.enums.Role;
import com.ftn.repository.CompanyDao;
import com.ftn.repository.WareGroupDao;
import com.ftn.service.AuthenticationService;
import com.ftn.service.WareGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jasmina on 22/05/2017.
 */
@Service
public class WareGroupServiceImplementation implements WareGroupService {

    private final WareGroupDao wareGroupDao;

    private final CompanyDao companyDao;

    private final AuthenticationService authenticationService;

    @Autowired
    public WareGroupServiceImplementation(WareGroupDao wareGroupDao, CompanyDao companyDao, AuthenticationService authenticationService) {
        this.wareGroupDao = wareGroupDao;
        this.companyDao = companyDao;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<WareGroup> read() {
        final User user = authenticationService.getCurrentUser();
        if (Role.EMPLOYEE.equals(user.getRole())) {
            final Company company = user.getCompany();
            return wareGroupDao.findByCompanyId(company.getId());
        } else {
            return wareGroupDao.findAll();
        }
    }

    @Override
    public WareGroup read(Long id) {
        return wareGroupDao.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public WareGroup create(WareGroup wareGroup) {
        if (wareGroupDao.findById(wareGroup.getId()).isPresent()) {
            throw new BadRequestException();
        }
        //zamenila sam redosled naredne dve linije jer mi vise ima smisla
        wareGroup.setCompany(companyDao.findById(wareGroup.getCompany().getId()).orElseThrow(NotFoundException::new));
        return wareGroupDao.save(wareGroup);
    }

    @Override
    public WareGroup update(Long id, WareGroup wareGroup) {
        final WareGroup persistentWareGroup = getWareGroup(id);
        wareGroup.setId(persistentWareGroup.getId());
        return wareGroupDao.save(wareGroup);
    }

    @Override
    public void delete(Long id) {
        final WareGroup wareGroup = getWareGroup(id);
        wareGroupDao.delete(wareGroup);
    }

    private WareGroup getWareGroup(Long id) {
        final User user = authenticationService.getCurrentUser();
        if (Role.EMPLOYEE.equals(user.getRole())) {
            final Company company = user.getCompany();
            return wareGroupDao.findByIdAndCompanyId(id, company.getId()).orElseThrow(NotFoundException::new);
        } else {
            return wareGroupDao.findById(id).orElseThrow(NotFoundException::new);
        }
    }
}
