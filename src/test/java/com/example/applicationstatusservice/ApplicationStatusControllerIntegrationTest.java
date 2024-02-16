package com.example.applicationstatusservice;

import com.example.applicationstatusservice.controller.ApplicationStatusController;
import com.example.applicationstatusservice.model.dto.ApplicationStatusDTO;
import com.example.applicationstatusservice.model.dto.PersonDTO;
import com.example.applicationstatusservice.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ApplicationStatusControllerIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest").withDatabaseName("postgrestest").withUsername("postgres").withPassword("Qwerty123456!");

    @Autowired
    private ApplicationStatusController applicationStatusController;

    @Autowired
    private PersonService personService;

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
        PersonDTO personDTO = new PersonDTO(5L, "Clara", "Eklund", "202203323434", "claraeklund@kth.com", "123", "claraek");
        personService.saveApplicant(personDTO);
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(5L, "Pending");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void personIdInvalid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(4000L, "Pending");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    void statusPendingValid() throws Exception {
        PersonDTO personDTO = new PersonDTO(6L, "Clara", "Eklund", "202203323434", "claraeklund@kth.com", "123", "claraek");
        personService.saveApplicant(personDTO);
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(6L, "Pending");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void statusAcceptValid() throws Exception {
        PersonDTO personDTO = new PersonDTO(7L, "Clara", "Eklund", "202203323434", "claraeklund@kth.com", "123", "claraek");
        personService.saveApplicant(personDTO);
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(7L, "Accept");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void statusRejectValid() throws Exception {
        PersonDTO personDTO = new PersonDTO(8L, "Clara", "Eklund", "202203323434", "claraeklund@kth.com", "123", "claraek");
        personService.saveApplicant(personDTO);
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(8L, "Reject");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void statusInvalid() throws Exception {
        PersonDTO personDTO = new PersonDTO(9L, "Clara", "Eklund", "202203323434", "claraeklund@kth.com", "123", "claraek");
        personService.saveApplicant(personDTO);
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(9L, "random");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }
}