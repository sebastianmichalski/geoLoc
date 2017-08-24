package com.geoloc.service;


import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.stereotype.Service;

/**
 * Interface of com.geoloc.service used for storing geo location
 */
@Service
public interface LocationService {

    /**
     * Stores geo location
     *
     * @param geoJson location as a JSON
     */
    void storeLocation(GeoJson geoJson);
}
