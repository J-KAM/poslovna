package com.ftn.repository;

import com.ftn.model.WareGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jasmina on 22/05/2017.
 */
public interface WareGroupDao extends JpaRepository<WareGroup, Long> {

    Optional<WareGroup> findById(Long id);

    Optional<WareGroup> findByIdAndCompanyId(Long id, Long companyId);

    List<WareGroup> findByCompanyId(Long id);
}
