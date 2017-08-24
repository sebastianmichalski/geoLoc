package com.geoloc.service;

import com.geoloc.repository.LocationRepository;
import com.mongodb.MongoException;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Service used for storing geo location
 */
@Service
public class LocationServiceImpl implements LocationService {
    public static final long BULK_SIZE = 10;
    private Queue<GeoJson> geoLocations = new LinkedList<>();

    @Autowired
    private LocationRepository locationRepository;

    /**
     * Stores bulk of geo locations
     *
     * @param geoJson location as a JSON
     */
    @Override
    @Synchronized
    public void storeLocation(GeoJson geoJson) {
        geoLocations.add(geoJson);
        if (geoLocations.size() >= BULK_SIZE) {
            final List<GeoJson> insertion = locationRepository.insert(geoLocations);
            if (insertion.size() == geoLocations.size()) {
                geoLocations.clear();
            } else {
                throw new MongoException("Some data was not inserted properly");
            }
        }
    }
}
