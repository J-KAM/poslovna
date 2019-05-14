package com.ftn.service;


import com.ftn.model.Company;

import java.util.List;

/**
 * Created by Jasmina on 21/05/2017.
 */
public interface CompanyService {

    List<Company> read();

    Company read(Long id);

    Company create(Company company);

    Company update(Long id, Company company);

    void delete(Long id);
}
