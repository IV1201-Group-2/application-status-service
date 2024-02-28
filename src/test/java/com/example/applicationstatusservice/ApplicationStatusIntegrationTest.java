package com.example.applicationstatusservice;

import com.example.applicationstatusservice.model.dto.ApplicationStatusDTO;
import com.example.applicationstatusservice.model.dto.PersonDTO;
import com.example.applicationstatusservice.repository.PersonRepository;
import com.example.applicationstatusservice.service.ApplicationStatusService;
import com.example.applicationstatusservice.service.JwtAuthService;
import com.example.applicationstatusservice.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
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

/**
 * ApplicationStatusIntegrationTest test uses TestContainers and demonstrates integration testing.
 * The tests included are:
 * 1. Checking if a valid or invalid person_id, received through ApplicationStatusDTO returns
 * the correct response message from the service-layer.
 * 2. Checking if a valid or invalid status, received through ApplicationStatusDTO returns
 * the correct response message from the service-layer.
 * 3. Checking if a valid or invalid JWT token, received through header returns
 * the correct response message from the service-layer.
 * {@code @Transactional} ensures application is saved to the database only if
 * the transaction is successful.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@Transactional
public class ApplicationStatusIntegrationTest {

    /**
     * Mocking a PostgreSQL database for the integration tests.
     * The database is configured with a specific, name, username and
     * password as well as the latest postgreSQL version.
     * {@code @Container} sets the field as a TestContainer container.
     */
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest").withDatabaseName("postgresglobalapp").withUsername("postgres").withPassword("Qwerty123456!");

    /**
     * ApplicationStatusService is an autowired instance containing business-logic for status-related operations.
     * {@code @Autowired} provides automatic dependency injection.
     */
    @Autowired
    private ApplicationStatusService applicationStatusService;

    /**
     * ApplicationStatusService is an autowired instance containing logic for authentication
     * and authorization of jwt tokens.
     * {@code @Autowired} provides automatic dependency injection.
     */
    @Autowired
    private JwtAuthService jwtAuthService;

    /**
     * PersonService is an autowired instance containing business-logic for person-related operations.
     * {@code @Autowired} provides automatic dependency injection.
     */
    @Autowired
    private PersonService personService;

    /**
     * PersonRepository is an autowired instance containing method for data
     * access/retrieval from the Person table.
     * {@code @Autowired} provides automatic dependency injection.
     */
    @Autowired
    private PersonRepository personRepository;

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
     * JUnit test to check if a valid person_id returns the correct response message from the service-layer.
     */
    @Test
    void personIdValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(5L, "Pending");
        assertEquals("VALID_DATA", applicationStatusService.isPersonIdValid(applicationStatusDTO.getPerson_id()));

    }

    /**
     * JUnit test to check if an invalid person_id returns the correct response message from the service-layer.
     */
    @Test
    void personIdInvalid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(4000L, "Pending");
        assertEquals("INVALID_DATA", applicationStatusService.isPersonIdValid(applicationStatusDTO.getPerson_id()));

    }

    /**
     * JUnit test to check if a valid status: Pending returns the correct response message from the service-layer.
     */
    @Test
    void statusPendingValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(7L, "Pending");
        assertEquals("VALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));

    }

    /**
     * JUnit test to check if a valid status: Accept returns the correct response message from the service-layer.
     */
    @Test
    void statusAcceptValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(1L, "Accept");
        assertEquals("VALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));

    }

    /**
     * JUnit test to check if a valid status: Reject returns the correct response message from the service-layer.
     */
    @Test
    void statusRejectValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(3L, "Reject");
        assertEquals("VALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));

    }

    /**
     * JUnit test to check if an invalid status returns the correct response message from the service-layer.
     */
    @Test
    void statusInvalid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(4L, "random");
        assertEquals("INVALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));
    }

    /**
     * JUnit test to check if a valid JWT token returns the correct response message from the service-layer.
     */
    @Test
    void jwtTokenValid() throws Exception {
        String testToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2FnZSI6ImxvZ2luIiwiaWQiOjUsInJvbGUiOjEsInVzZXJuYW1lIjoiTWF4d2VsbEJhaWxleSIsImV4cCI6MTcwOTA2MzA2MSwiaWF0IjoxNzA5MDU5NDYxfQ.lYZASF-3vemdk0_XGF_HhOmig4UR4PUfkoW0nf0-EZw";
        String testHeader = "Bearer " + testToken;
        assertEquals("AUTHORIZED", jwtAuthService.jwtAuth(testHeader));
    }

    /**
     * JUnit test to check if an invalid JWT token returns the correct response message from the service-layer.
     */
    @Test
    void jwtTokenInValid() throws Exception {
        String testToken = "yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2FnZSI6ImxvZ2luIiwiaWQiOjUsInJvbGUiOjEsInVzZXJuYW1lIjoiTWF4d2VsbEJhaWxleSIsImV4cCI6MTcwOTA2MzA2MSwiaWF0IjoxNzA5MDU5NDYxfQ.lYZASF-3vemdk0_XGF_HhOmig4UR4PUfkoW0nf0-EZw";
        String testHeader = "Bearer " + testToken;
        assertEquals("UNAUTHORIZED", jwtAuthService.jwtAuth(testHeader));
    }

}