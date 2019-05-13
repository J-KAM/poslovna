package com.ftn.service;

import com.ftn.model.dto.DocumentDTO;



/**
 * Created by Jasmina on 14/06/2017.
 */
public interface BookingService {

    DocumentDTO book(Long id, DocumentDTO documentDTO);
}
