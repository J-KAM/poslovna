package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.WarehouseCardAnalytics;
import com.ftn.model.dto.WarehouseCardAnalyticsDTO;
import com.ftn.repository.WarehouseCardAnalyticsDao;
import com.ftn.service.WarehouseCardAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 31.5.2017..
 */
@Service
public class WarehouseCardAnalyticsServiceImplementation implements WarehouseCardAnalyticsService {

    private final WarehouseCardAnalyticsDao warehouseCardAnalyticsDao;

    @Autowired
    public WarehouseCardAnalyticsServiceImplementation(WarehouseCardAnalyticsDao warehouseCardAnalyticsDao) {
        this.warehouseCardAnalyticsDao = warehouseCardAnalyticsDao;
    }


    @Override
    public List<WarehouseCardAnalyticsDTO> read() {
        return warehouseCardAnalyticsDao.findAll().stream().map(WarehouseCardAnalyticsDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<WarehouseCardAnalyticsDTO> read(Long warehouseCardId) {
        return warehouseCardAnalyticsDao.findByWarehouseCardId(warehouseCardId).stream().map(WarehouseCardAnalyticsDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<WarehouseCardAnalyticsDTO> read(Long id, WarehouseCardAnalytics.TrafficType trafficType) {
        return warehouseCardAnalyticsDao.findByWarehouseCardIdAndTrafficTypeOrderByCreatedDesc(id,trafficType).stream().map(WarehouseCardAnalyticsDTO::new).collect(Collectors.toList());
    }

    @Override
    public WarehouseCardAnalyticsDTO create(WarehouseCardAnalyticsDTO warehouseCardAnalyticsDTO) {
        if (warehouseCardAnalyticsDao.findById(warehouseCardAnalyticsDTO.getId()).isPresent()) {
            throw new BadRequestException();
        }
        final WarehouseCardAnalytics warehouseCardAnalytics = warehouseCardAnalyticsDTO.construct();
        warehouseCardAnalyticsDao.save(warehouseCardAnalytics);
        return new WarehouseCardAnalyticsDTO(warehouseCardAnalytics);
    }

    @Override
    public WarehouseCardAnalyticsDTO update(Long id, WarehouseCardAnalyticsDTO warehouseCardAnalyticsDTO) {
        final WarehouseCardAnalytics warehouseCardAnalytics = warehouseCardAnalyticsDao.findById(id).orElseThrow(NotFoundException::new);
        warehouseCardAnalytics.merge(warehouseCardAnalyticsDTO);
        warehouseCardAnalyticsDao.save(warehouseCardAnalytics);
        return new WarehouseCardAnalyticsDTO(warehouseCardAnalytics);
    }


    @Override
    public void delete(Long id) {
        final WarehouseCardAnalytics warehouseCardAnalytics = warehouseCardAnalyticsDao.findById(id).orElseThrow(NotFoundException::new);
        warehouseCardAnalyticsDao.delete(warehouseCardAnalytics);
    }
}
