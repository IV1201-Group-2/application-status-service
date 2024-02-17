package com.example.applicationstatusservice;

import com.example.applicationstatusservice.controller.ApplicationStatusController;
import com.example.applicationstatusservice.model.Person;
import com.example.applicationstatusservice.model.dto.ApplicationStatusDTO;
import com.example.applicationstatusservice.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@Transactional
public class ApplicationStatusControllerIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest").withDatabaseName("postgresglobalapp").withUsername("postgres").withPassword("Qwerty123456!");

    @Autowired
    private ApplicationStatusController applicationStatusController;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EntityManager entityManager;

    @DynamicPropertySource
    public static void testProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);

    }

    @Test
    void mockTest() {
        System.out.println("Is container running?");
    }

    @BeforeEach
    void setup() {
        Person person = new Person();
        person.setPerson_id(1L);
        person.setName("Clara");
        person.setSurname("Eklund");
        person.setPnr("200101012923");
        person.setEmail("clara@kth.se");
        person.setPassword("123");
        person.setUsername("claraek");
        personRepository.save(person);
    }


    @Test
    void personIdValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(1L, "Pending");
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
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(1L, "Pending");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void statusAcceptValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(1L, "Accept");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void statusRejectValid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(1L, "Reject");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void statusInvalid() throws Exception {
        ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO(1L, "random");
        ResponseEntity<Object> resp = applicationStatusController.handleApplicationStatus(applicationStatusDTO);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }
}