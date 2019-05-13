package com.ftn.service;

import com.ftn.model.dto.LocationDTO;

import java.util.List;

/**
 * Created by Jasmina on 21/05/2017.
 */
public interface LocationService {

    List<LocationDTO> read();

    LocationDTO create(LocationDTO locationDTO);

    LocationDTO update(Long id, LocationDTO locationDTO);

    void delete(Long id);
}
