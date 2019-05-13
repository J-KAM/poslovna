package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.MeasurementUnit;
import com.ftn.model.dto.MeasurementUnitDTO;
import com.ftn.repository.MeasurementUnitDao;
import com.ftn.service.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
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
    public List<MeasurementUnitDTO> read() {
        return measurementUnitDao.findAll().stream().map(MeasurementUnitDTO::new).collect(Collectors.toList());
    }

    @Override
    public MeasurementUnitDTO create(MeasurementUnitDTO measurementUnitDTO) {
        if (measurementUnitDao.findById(measurementUnitDTO.getId()).isPresent()) {
            throw new BadRequestException();
        }
        final MeasurementUnit measurementUnit = measurementUnitDTO.construct();
        measurementUnitDao.save(measurementUnit);
        return new MeasurementUnitDTO(measurementUnit);
    }

    @Override
    public MeasurementUnitDTO update(Long id, MeasurementUnitDTO measurementUnitDTO) {
        final MeasurementUnit measurementUnit = measurementUnitDao.findById(id).orElseThrow(NotFoundException::new);
        measurementUnit.merge(measurementUnitDTO);
        measurementUnitDao.save(measurementUnit);
        return new MeasurementUnitDTO(measurementUnit);
    }

    @Override
    public void delete(Long id) {
        final MeasurementUnit measurementUnit = measurementUnitDao.findById(id).orElseThrow(NotFoundException::new);
        measurementUnitDao.delete(measurementUnit);
    }


}
