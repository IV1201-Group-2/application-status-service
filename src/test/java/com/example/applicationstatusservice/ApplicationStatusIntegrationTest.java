package com.example.applicationstatusservice;

import com.example.applicationstatusservice.model.dto.ApplicationStatusDTO;
import com.example.applicationstatusservice.service.ApplicationStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@Transactional
public class ApplicationStatusIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest").withDatabaseName("postgresg2").withUsername("postgres").withPassword("Qwerty123456!");

    @Autowired
    private ApplicationStatusService applicationStatusService;

    @DynamicPropertySource
    public static void testProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);

    }

    @Test
    void mockTest() {
        System.out.println("Is container running?");
    }

    @Test
    void personIdValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(11L, "Pending");
        assertEquals("VALID_DATA", applicationStatusService.isPersonIdValid(applicationStatusDTO.getPerson_id()));

    }

    @Test
    void personIdInvalid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(4000L, "Pending");
        assertEquals("INVALID_DATA", applicationStatusService.isPersonIdValid(applicationStatusDTO.getPerson_id()));

    }

    @Test
    void statusPendingValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(11L, "Pending");
        assertEquals("VALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));

    }

    @Test
    void statusAcceptValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(11L, "Accept");
        assertEquals("VALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));

    }

    @Test
    void statusRejectValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(11L, "Reject");
        assertEquals("VALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));

    }

    @Test
    void statusInvalid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(11L, "random");
        assertEquals("INVALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));

    }
}

