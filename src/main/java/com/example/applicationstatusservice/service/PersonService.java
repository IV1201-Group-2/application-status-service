package com.example.applicationstatusservice.service;

import com.example.applicationstatusservice.controller.ApplicationStatusController;
import com.example.applicationstatusservice.model.Person;
import com.example.applicationstatusservice.model.dto.PersonDTO;
import com.example.applicationstatusservice.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PersonService is a service class meant to handle the business-logic
 * specific to person-related operations.
 */
@Service
public class PersonService {

    /**
     * Logger to log events passing the person-registration business-logic.
     */
    private static final Logger logger = LogManager.getLogger(PersonService.class);


    /**
     * personRepository is a repository that handles data retrieval/modification operations.
     */
    private final PersonRepository personRepository;

    /**
     * Constructor for the personService class.
     * {@code @Autowired} provides automatic dependency injection.
     *
     * @param personRepository handles handles data retrieval/modification operations.
     */
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Saves a person when they have correctly registered/passed
     * validation checks to the application.
     * {@code @Transactional} ensures application is saved to the
     * database only if the transaction is successful.
     *
     * @param personDTO Data transfer object
     *                  representing users submitted information.
     */
    @Transactional
    public void saveApplicant(PersonDTO personDTO) {
        Person person = Person.builder().name(personDTO.getName()).surname(personDTO.getSurname()).pnr(personDTO.getPnr()).email(personDTO.getEmail()).password(personDTO.getPassword()).role_id(2).username(personDTO.getUsername()).build();
        personRepository.save(person);
        logger.info("A new person has registered with username: {}", personDTO.getUsername());
    }
}
