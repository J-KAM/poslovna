package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.MeasurementUnit;
import com.ftn.repository.MeasurementUnitDao;
import com.ftn.service.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by Alex on 5/20/17.
 */
@Service
public class MeasurementUnitServiceImplementation implements MeasurementUnitService {

    private final MeasurementUnitDao measurementUnitDao;

    @Autowired
    public MeasurementUnitServiceImplementation(MeasurementUnitDao measurementUnitDao) {
        this.measurementUnitDao = measurementUnitDao;
    }

    @Override
    public List<MeasurementUnit> read() {
        return measurementUnitDao.findAll();
    }

    @Override
    public MeasurementUnit create(MeasurementUnit measurementUnit) {
        if (measurementUnitDao.findById(measurementUnit.getId()).isPresent()) {
            throw new BadRequestException();
        }
        measurementUnitDao.save(measurementUnit);
        return measurementUnitDao.save(measurementUnit);
    }

    @Override
    public MeasurementUnit update(Long id, MeasurementUnit measurementUnit) {
        measurementUnitDao.findById(id).orElseThrow(NotFoundException::new);
        measurementUnit.setId(id);
        return  measurementUnitDao.save(measurementUnit);
    }

    @Override
    public void delete(Long id) {
        final MeasurementUnit measurementUnit = measurementUnitDao.findById(id).orElseThrow(NotFoundException::new);
        measurementUnitDao.delete(measurementUnit);
    }


}
