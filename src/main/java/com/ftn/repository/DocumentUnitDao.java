package com.ftn.repository;

import com.ftn.model.DocumentUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Alex on 5/20/17.
 */
public interface DocumentUnitDao extends JpaRepository<DocumentUnit, Long> {

    Optional<DocumentUnit> findById(Long id);

    List<DocumentUnit> findByDocumentId(Long id);
}
