package com.geoloc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Entry point to application
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.geoloc.*"})
public class LocationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationsApplication.class, args);
    }
}
