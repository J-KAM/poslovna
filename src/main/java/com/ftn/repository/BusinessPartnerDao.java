package com.ftn.repository;

import com.ftn.model.BusinessPartner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Olivera on 21.5.2017..
 */
public interface BusinessPartnerDao extends JpaRepository<BusinessPartner, Long> {

    Optional<BusinessPartner> findById(Long id);

    List<BusinessPartner> findByCompanyId(Long id);
}
