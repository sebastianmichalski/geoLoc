package com.geoloc.repository;

import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to access MongoDB geoJson collection
 */
@Repository
public interface LocationRepository extends MongoRepository<GeoJson, String> {
}
