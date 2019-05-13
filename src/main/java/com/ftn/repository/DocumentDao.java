package com.ftn.repository;

import com.ftn.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Alex on 5/21/17.
 */
public interface DocumentDao extends JpaRepository<Document, Long> {

    Optional<Document> findById(Long id);

    Optional<Document> findByIdAndWarehouseEmployeeId(Long id, Long employeeId);

    List<Document> findByWarehouseEmployeeId(Long id);

    List<Document> findByWarehouseEmployeeIdAndBusinessYearId(Long employeeId, Long businessYearId);

    List<Document> findByWarehouseId(Long id);
}
