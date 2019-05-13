package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Location;
import com.ftn.model.dto.LocationDTO;
import com.ftn.repository.LocationDao;
import com.ftn.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<LocationDTO> read() {
        return locationDao.findAll().stream().map(LocationDTO::new).collect(Collectors.toList());
    }

    @Override
    public LocationDTO create(LocationDTO locationDTO) {
        if (locationDao.findById(locationDTO.getId()).isPresent()) {
            throw new BadRequestException();
        }
        final Location location = locationDTO.construct();
        locationDao.save(location);
        return new LocationDTO(location);
    }

    @Override
    public LocationDTO update(Long id, LocationDTO locationDTO) {
        final Location location = locationDao.findById(id).orElseThrow(NotFoundException::new);
        location.merge(locationDTO);
        locationDao.save(location);
        return new LocationDTO(location);
    }

    @Override
    public void delete(Long id) {
        final Location location = locationDao.findById(id).orElseThrow(NotFoundException::new);
        locationDao.delete(location);
    }
}
