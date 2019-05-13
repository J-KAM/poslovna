package com.ftn.repository;

import com.ftn.model.Ware;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by JELENA on 5/21/17.
 */
public interface WareDao extends JpaRepository<Ware, Long> {

    Optional<Ware> findById(Long id);

    Optional<Ware> findByIdAndWareGroupCompanyId(Long id, long companyId);

    List<Ware> findByWareGroupCompanyId(long id);
}
