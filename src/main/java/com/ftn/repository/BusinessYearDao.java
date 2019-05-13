package com.ftn.repository;

import com.ftn.model.BusinessYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by JELENA on 30.5.2017.
 */
public interface BusinessYearDao extends JpaRepository<BusinessYear, Long> {

    Optional<BusinessYear> findById(Long id);

    Optional<BusinessYear> findFirstByOrderByYearDesc();

    List<BusinessYear> findByClosed(boolean closed);

    List<BusinessYear> findByCompanyId(Long companyId);
}
