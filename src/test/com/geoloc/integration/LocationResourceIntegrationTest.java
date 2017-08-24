package com.geoloc.integration;


import com.geoloc.LocationsApplication;
import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Timer;

import static com.geoloc.service.LocationServiceImpl.BULK_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Integration tests for {@link LocationResource}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LocationsApplication.class)
@WebAppConfiguration
public class LocationResourceIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MongoClient mongo = new MongoClient("localhost", 27017);

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        mongo.getDatabase("test").drop();
    }

    @Test
    public void shouldStoreBulkOfData() throws Exception {
        // when
        for (int i = 0; i < BULK_SIZE; i++) {
            mvc.perform(put("/").param("lat", "51.213").param("long", "21.123")).andExpect(status().isOk());
        }
        // then
        assertEquals(mongo.getDatabase("test").getCollection("geoJsonPoint").count(), BULK_SIZE);
    }

    @Test
    public void shouldNotStoreLessDataThanPresetBulkSize() throws Exception {
        // when
        for (int i = 0; i < BULK_SIZE-1; i++) {
            mvc.perform(put("/").param("lat", "51.213").param("long", "21.123")).andExpect(status().isOk());
        }
        // then
        assertEquals(mongo.getDatabase("test").getCollection("geoJsonPoint").count(), 0);
    }

    @Test
    public void should() throws Exception {
        // given
        long startTime = System.currentTimeMillis();

        // when
        for (int i = 0; i < 50_000; i++) {
            mvc.perform(put("/").param("lat", "51.213").param("long", "21.123")).andExpect(status().isOk());
        }
        long estimatedTime = System.currentTimeMillis() - startTime;

        // then
        assertTrue(estimatedTime < 15_000L);
        assertEquals(mongo.getDatabase("test").getCollection("geoJsonPoint").count(), 50_000);
    }
}
