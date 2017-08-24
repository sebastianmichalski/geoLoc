package com.geoloc.rest;

import com.geoloc.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST service used to store geo location
 */
@RestController
public class LocationResource {

    @Autowired
    private LocationService locationService;

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public final void storeLocation(@RequestParam("lat") Float latitude,
                                    @RequestParam("long") Float longitude) {
        locationService.storeLocation(new GeoJsonPoint(latitude, longitude));
    }


}
