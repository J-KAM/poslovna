package com.ftn.service;

import com.ftn.model.dto.MeasurementUnitDTO;

import java.util.List;

/**
 * Created by Alex on 5/20/17.
 */
public interface MeasurementUnitService {

    List<MeasurementUnitDTO> read();

    MeasurementUnitDTO create(MeasurementUnitDTO measurementUnitDTO);

    MeasurementUnitDTO update(Long id, MeasurementUnitDTO measurementUnitDTO);

    void delete(Long id);
}
