package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.WarehouseCardAnalytics;
import com.ftn.model.enums.TrafficType;
import com.ftn.repository.WarehouseCardAnalyticsDao;
import com.ftn.service.WarehouseCardAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<WarehouseCardAnalytics> read() {
        return warehouseCardAnalyticsDao.findAll();
    }

    @Override
    public WarehouseCardAnalytics read(Long id) {
        return warehouseCardAnalyticsDao.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<WarehouseCardAnalytics> readByWarehouseCardId(Long warehouseCardId) {
        return warehouseCardAnalyticsDao.findByWarehouseCardId(warehouseCardId);
    }

    @Override
    public List<WarehouseCardAnalytics> read(Long id, TrafficType trafficType) {
        return warehouseCardAnalyticsDao.findByWarehouseCardIdAndTrafficTypeOrderByCreatedDesc(id,trafficType);
    }

    @Override
    public WarehouseCardAnalytics create(WarehouseCardAnalytics warehouseCardAnalytics) {
        if (warehouseCardAnalyticsDao.findById(warehouseCardAnalytics.getId()).isPresent()) {
            throw new BadRequestException();
        }
        return warehouseCardAnalyticsDao.save(warehouseCardAnalytics);

    }

    @Override
    public WarehouseCardAnalytics update(Long id, WarehouseCardAnalytics warehouseCardAnalytics) {
        warehouseCardAnalyticsDao.findById(id).orElseThrow(NotFoundException::new);
        warehouseCardAnalytics.setId(id);
        return warehouseCardAnalyticsDao.save(warehouseCardAnalytics);
    }


    @Override
    public void delete(Long id) {
        final WarehouseCardAnalytics warehouseCardAnalytics = warehouseCardAnalyticsDao.findById(id).orElseThrow(NotFoundException::new);
        warehouseCardAnalyticsDao.delete(warehouseCardAnalytics);
    }
}
