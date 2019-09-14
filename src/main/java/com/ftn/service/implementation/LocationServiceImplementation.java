package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Location;
import com.ftn.repository.LocationDao;
import com.ftn.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jasmina on 21/05/2017.
 */
@Service
public class LocationServiceImplementation implements LocationService {

    private final LocationDao locationDao;

    @Autowired
    public LocationServiceImplementation(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Override
    public List<Location> read() {
        return locationDao.findAll();
    }

    @Override
    public Location read(Long id) {
        return locationDao.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Location create(Location location) {
        if (locationDao.findById(location.getId()).isPresent()) {
            throw new BadRequestException();
        }
        return locationDao.save(location);
    }

    @Override
    public Location update(Long id, Location location) {
        locationDao.findById(id).orElseThrow(NotFoundException::new);
        location.setId(id);
        return locationDao.save(location);
    }

    @Override
    public void delete(Long id) {
        final Location location = locationDao.findById(id).orElseThrow(NotFoundException::new);
        locationDao.delete(location);
    }
}
