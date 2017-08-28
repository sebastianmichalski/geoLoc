package com.geoloc.service;

import com.geoloc.repository.LocationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;
import java.util.Queue;

import static com.geoloc.service.LocationServiceImpl.BULK_SIZE;
import static java.lang.Math.toIntExact;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTest {

    private LocationService locationService;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private List dummyCollection;

    @Before
    public void setUp() {
        locationService = new LocationServiceImpl(locationRepository);
    }

    @Test
    public void shouldStoreLocationInInternalVariableAndDoNotCallRepository() {
        // given
        final GeoJsonPoint geoJson = new GeoJsonPoint(51.3d, 21.1d);
        final Queue<GeoJson> geoLocations = (Queue<GeoJson>) Whitebox.getInternalState(locationService, "geoLocations");

        // when
        locationService.storeLocation(geoJson);

        // then
        assertEquals(geoLocations.size(), 1);
        verify(locationRepository, never()).insert(anyCollection());
    }

    @Test
    public void shouldStoreLocationsInMongoDbAfterBulkSizeIsReached() {
        // given
        final GeoJsonPoint geoJson = new GeoJsonPoint(51.3d, 21.1d);
        final Queue<GeoJson> geoLocations = (Queue<GeoJson>) Whitebox.getInternalState(locationService, "geoLocations");
        when(locationRepository.insert(anyCollection())).thenReturn(dummyCollection);
        when(dummyCollection.size()).thenReturn(toIntExact(BULK_SIZE));

        // when
        for (int i = 0; i < BULK_SIZE; i++) {
            locationService.storeLocation(geoJson);
        }

        // then
        assertEquals(geoLocations.size(), 0);
        verify(locationRepository, times(1)).insert(geoLocations);
    }

}
