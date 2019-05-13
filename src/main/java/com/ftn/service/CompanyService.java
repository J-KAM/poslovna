package com.ftn.service;


import com.ftn.model.dto.CompanyDTO;
import com.ftn.model.dto.UserDTO;

import java.util.List;

/**
 * Created by Jasmina on 21/05/2017.
 */
public interface CompanyService {

    List<CompanyDTO> read();

    CompanyDTO read(Long id);

    CompanyDTO create(CompanyDTO companyDTO);

    CompanyDTO update(Long id, CompanyDTO companyDTO);

    void delete(Long id);
}
