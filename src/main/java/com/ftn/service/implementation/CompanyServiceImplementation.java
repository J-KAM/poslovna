package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Company;
import com.ftn.model.Employee;
import com.ftn.model.User;
import com.ftn.model.dto.CompanyDTO;
import com.ftn.model.dto.UserDTO;
import com.ftn.model.dto.WareDTO;
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
    public List<CompanyDTO> read() {
        final User user = authenticationService.getCurrentUser();
        if (user instanceof Employee) {
            final Company company = ((Employee) user).getCompany();
            final CompanyDTO companyDTO = new CompanyDTO(company, true);
            final List<CompanyDTO> companyDTOS = new ArrayList<>();
            companyDTOS.add(companyDTO);
            return companyDTOS;
        } else {
            return companyDao.findAll().stream().map(CompanyDTO::new).collect(Collectors.toList());
        }
    }

    @Override
    public CompanyDTO read(Long id) {
        return new CompanyDTO(companyDao.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public CompanyDTO create(CompanyDTO companyDTO) {
        if (companyDao.findById(companyDTO.getId()).isPresent()) {
            throw new BadRequestException();
        }
        final Company company = companyDTO.construct();
        companyDao.save(company);
        company.setLocation(locationDao.findById(company.getLocation().getId()).orElseThrow(NotFoundException::new));
        return new CompanyDTO(company);
    }

    @Override
    public CompanyDTO update(Long id, CompanyDTO companyDTO) {
        final Company company = companyDao.findById(id).orElseThrow(NotFoundException::new);
        company.merge(companyDTO);
        companyDao.save(company);
        return new CompanyDTO(company);
    }

    @Override
    public void delete(Long id) {
        final Company company = companyDao.findById(id).orElseThrow(NotFoundException::new);
        companyDao.delete(company);
    }
}
