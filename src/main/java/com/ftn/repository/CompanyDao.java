package com.ftn.repository;

import com.ftn.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Jasmina on 21/05/2017.
 */
public interface CompanyDao extends JpaRepository<Company, Long> {

    Optional<Company> findById(Long id);

}
