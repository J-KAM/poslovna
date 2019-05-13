package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Company;
import com.ftn.model.Employee;
import com.ftn.model.User;
import com.ftn.model.WareGroup;
import com.ftn.model.dto.WareGroupDTO;
import com.ftn.repository.CompanyDao;
import com.ftn.repository.WareGroupDao;
import com.ftn.service.AuthenticationService;
import com.ftn.service.WareGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<WareGroupDTO> read() {
        final User user = authenticationService.getCurrentUser();
        if (user instanceof Employee) {
            final Company company = ((Employee) user).getCompany();
            return wareGroupDao.findByCompanyId(company.getId()).stream().map(WareGroupDTO::new).collect(Collectors.toList());
        } else {
            return wareGroupDao.findAll().stream().map(WareGroupDTO::new).collect(Collectors.toList());
        }
    }

    @Override
    public WareGroupDTO create(WareGroupDTO wareGroupDTO) {
        if (wareGroupDao.findById(wareGroupDTO.getId()).isPresent()) {
            throw new BadRequestException();
        }
        final WareGroup wareGroup = wareGroupDTO.construct();
        wareGroupDao.save(wareGroup);
        wareGroup.setCompany(companyDao.findById(wareGroup.getCompany().getId()).orElseThrow(NotFoundException::new));
        return new WareGroupDTO(wareGroup);
    }

    @Override
    public WareGroupDTO update(Long id, WareGroupDTO wareGroupDTO) {
        final WareGroup wareGroup = getWareGroup(id);
        wareGroup.merge(wareGroupDTO);
        wareGroupDao.save(wareGroup);
        return new WareGroupDTO(wareGroup);
    }

    @Override
    public void delete(Long id) {
        final WareGroup wareGroup = getWareGroup(id);
        wareGroupDao.delete(wareGroup);
    }

    private WareGroup getWareGroup(Long id) {
        final User user = authenticationService.getCurrentUser();
        if (user instanceof Employee) {
            final Company company = ((Employee) user).getCompany();
            return wareGroupDao.findByIdAndCompanyId(id, company.getId()).orElseThrow(NotFoundException::new);
        } else {
            return wareGroupDao.findById(id).orElseThrow(NotFoundException::new);
        }
    }
}
