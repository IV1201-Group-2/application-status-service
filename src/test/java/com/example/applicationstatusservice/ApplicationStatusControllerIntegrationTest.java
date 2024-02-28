package com.example.applicationstatusservice;

import com.example.applicationstatusservice.controller.ApplicationStatusController;
import com.example.applicationstatusservice.model.dto.ApplicationStatusDTO;
import com.example.applicationstatusservice.model.dto.PersonDTO;
import com.example.applicationstatusservice.repository.PersonRepository;
import com.example.applicationstatusservice.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ApplicationStatusControllerIntegrationTest test uses TestContainers and demonstrates integration testing.
 * The tests included are:
 * 1. Checking if a valid or invalid person_id, received through ApplicationStatusDTO returns
 * the correct HTTP status response.
 * 2. Checking if a valid or invalid status, received through ApplicationStatusDTO returns
 * the correct HTTP status response.
 * {@code @Transactional} ensures application is saved to the database only if
 * the transaction is successful.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@Transactional
public class ApplicationStatusControllerIntegrationTest {

    /**
     * Mocking a PostgreSQL database for the integration tests.
     * The database is configured with a specific, name, username and
     * password as well as the latest postgreSQL version.
     * {@code @Container} sets the field as a TestContainer container.
     */
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest").withDatabaseName("postgresglobalapp").withUsername("postgres").withPassword("Qwerty123456!");

    /**
     * ApplicationStatusController is an autowired instance of the controller handling HTTP requests.
     */
    @Autowired
    private ApplicationStatusController applicationStatusController;

    /**
     * PersonService is an autowired instance containing business-logic for person-related operations.
     * {@code @Autowired} provides automatic dependency injection.
     */
    @Autowired
    PersonService personService;

    /**
     * PersonRepository is an autowired instance handling data retrieval/access for the Person table.
     */
    @Autowired
    PersonRepository personRepository;

    /**
     * The method sets the property JDBC URL spring.datasource.url
     * dynamically for the postgreSQL container.
     *
     * @param dynamicPropertyRegistry adding dynamic properties.
     *                                {@code @DynamicPropertySource} allows adding properties with dynamic values for test
     */
    @DynamicPropertySource
    public static void testProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);

    }

    /**
     * JUnit test annotated with {@code @Test} to see if the container is running.
     */
    @Test
    void mockTest() {
        System.out.println("Is container running?");
    }

    /**
     * {@code @BeforeEach} Annotation ensures this method runs and fills
     * the database with a person entity before each test.
     */
    @BeforeEach
    void saveAPerson() {
        PersonDTO personDTO = new PersonDTO("Clara", "Eklund", "202203323434", "claraeklund@kth.com", "123", "claraek");
        personService.saveApplicant(personDTO);

        System.out.println("person" + personRepository.findByUsername("claraek"));
    }

    /**
     * JUnit test to check if a valid person_id returns the correct HTTP Status response.
     */
    @Test
    void personIdValid() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("X-Forwarded-For", "127.0.0.1");

        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(5L, "Pending");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO, req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    /**
     * JUnit test to check if an invalid person_id returns the correct HTTP Status response.
     */
    @Test
    void personIdInvalid() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("X-Forwarded-For", "127.0.0.1");

        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(4000L, "Pending");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO, req);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    /**
     * JUnit test to check if a valid status: Pending returns the correct HTTP Status response.
     */
    @Test
    void statusPendingValid() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("X-Forwarded-For", "127.0.0.1");

        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(7L, "Pending");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO, req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    /**
     * JUnit test to check if a valid status: Accept returns the correct HTTP Status response.
     */
    @Test
    void statusAcceptValid() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("X-Forwarded-For", "127.0.0.1");

        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(1L, "Accept");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO, req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    /**
     * JUnit test to check if a valid status: Reject returns the correct HTTP Status response.
     */
    @Test
    void statusRejectValid() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("X-Forwarded-For", "127.0.0.1");

        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(3L, "Reject");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO, req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    /**
     * JUnit test to check if an invalid status returns the correct HTTP Status response.
     */
    @Test
    void statusInvalid() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("X-Forwarded-For", "127.0.0.1");

        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(4L, "random");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO, req);
    }
}