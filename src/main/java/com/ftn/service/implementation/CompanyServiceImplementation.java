package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Company;
import com.ftn.model.Employee;
import com.ftn.model.User;
import com.ftn.repository.CompanyDao;
import com.ftn.repository.LocationDao;
import com.ftn.service.AuthenticationService;
import com.ftn.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jasmina on 21/05/2017.
 */
@Service
public class CompanyServiceImplementation implements CompanyService {

    private final CompanyDao companyDao;

    private final LocationDao locationDao;

    private final AuthenticationService authenticationService;

    @Autowired
    public CompanyServiceImplementation(CompanyDao companyDao, LocationDao locationDao, AuthenticationService authenticationService) {
        this.companyDao = companyDao;
        this.locationDao = locationDao;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<Company> read() {
        final User user = authenticationService.getCurrentUser();
        if (user instanceof Employee) {
            final Company company = ((Employee) user).getCompany();
            final List<Company> companies = new ArrayList<>();
            companies.add(company);
            return companies;
        } else {
            return companyDao.findAll();
        }
    }

    @Override
    public Company read(Long id) {
        return new Company(companyDao.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Company create(Company company) {
        if (companyDao.findById(company.getId()).isPresent()) {
            throw new BadRequestException();
        }
        companyDao.save(company);
        company.setLocation(locationDao.findById(company.getLocation().getId()).orElseThrow(NotFoundException::new));
        return company;
    }

    @Override
    public Company update(Long id, Company company) {
        companyDao.findById(id).orElseThrow(NotFoundException::new);
        return companyDao.save(company);
    }

    @Override
    public void delete(Long id) {
        final Company company = companyDao.findById(id).orElseThrow(NotFoundException::new);
        companyDao.delete(company);
    }
}
