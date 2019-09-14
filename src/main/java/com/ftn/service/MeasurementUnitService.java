package com.ftn.service;

import com.ftn.model.MeasurementUnit;

import java.util.List;

/**
 * Created by Alex on 5/20/17.
 */
public interface MeasurementUnitService {

    List<MeasurementUnit> read();

    MeasurementUnit read(Long id);

    MeasurementUnit create(MeasurementUnit measurementUnit);

    MeasurementUnit update(Long id, MeasurementUnit measurementUnit);

    void delete(Long id);
}
