package com.ftn.service;

import com.ftn.model.Location;

import java.util.List;

/**
 * Created by Jasmina on 21/05/2017.
 */
public interface LocationService {

    List<Location> read();

    Location read(Long id);

    Location create(Location location);

    Location update(Long id, Location location);

    void delete(Long id);
}
