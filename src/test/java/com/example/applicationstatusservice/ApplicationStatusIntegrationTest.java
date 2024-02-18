package com.example.applicationstatusservice;

import com.example.applicationstatusservice.model.dto.ApplicationStatusDTO;
import com.example.applicationstatusservice.model.dto.PersonDTO;
import com.example.applicationstatusservice.repository.PersonRepository;
import com.example.applicationstatusservice.service.ApplicationStatusService;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@Transactional
public class ApplicationStatusIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest").withDatabaseName("postgresglobalapp").withUsername("postgres").withPassword("Qwerty123456!");

    @Autowired
    private ApplicationStatusService applicationStatusService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @DynamicPropertySource
    public static void testProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);

    }

    @Test
    void mockTest() {
        System.out.println("Is container running?");
    }

    @BeforeEach
    void saveAPerson() {
        PersonDTO personDTO = new PersonDTO("Clara", "Eklund", "202203323434", "claraeklund@kth.com", "123", "claraek");
        personService.saveApplicant(personDTO);

        System.out.println("person" + personRepository.findByUsername("claraek"));
    }

    @Test
    void personIdValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(5L, "Pending");
        assertEquals("VALID_DATA", applicationStatusService.isPersonIdValid(applicationStatusDTO.getPerson_id()));

    }

    @Test
    void personIdInvalid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(4000L, "Pending");
        assertEquals("INVALID_DATA", applicationStatusService.isPersonIdValid(applicationStatusDTO.getPerson_id()));

    }

    @Test
    void statusPendingValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(7L, "Pending");
        assertEquals("VALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));

    }

    @Test
    void statusAcceptValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(1L, "Accept");
        assertEquals("VALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));

    }

    @Test
    void statusRejectValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(3L, "Reject");
        assertEquals("VALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));

    }

    @Test
    void statusInvalid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(4L, "random");
        assertEquals("INVALID_DATA", applicationStatusService.isStatusValid(applicationStatusDTO.getStatus()));
    }
}