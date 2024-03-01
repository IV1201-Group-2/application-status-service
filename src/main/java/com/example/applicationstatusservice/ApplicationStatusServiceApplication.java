package com.example.applicationstatusservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ApplicationStatusServiceApplication is the class used to initialize and run the
 * application-status-service microservice for the project IV1201VT24.
 */
@SpringBootApplication
public class ApplicationStatusServiceApplication {

    /**
     * Main method used to start the application-status-service microservice.
     *
     * @param args is the command-line argument.
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStatusServiceApplication.class, args);
    }

}
