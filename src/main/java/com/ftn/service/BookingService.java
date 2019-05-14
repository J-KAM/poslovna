package com.ftn.service;

import com.ftn.model.Document;



/**
 * Created by Jasmina on 14/06/2017.
 */
public interface BookingService {

    Document book(Long id, Document document);
}
