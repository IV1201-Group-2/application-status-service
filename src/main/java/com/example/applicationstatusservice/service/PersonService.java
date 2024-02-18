package com.example.applicationstatusservice.service;

import com.example.applicationstatusservice.model.Person;
import com.example.applicationstatusservice.model.dto.PersonDTO;
import com.example.applicationstatusservice.repository.PersonRepository;
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
        Person person = Person.builder()
                .name(personDTO.getName())
                .surname(personDTO.getSurname())
                .pnr(personDTO.getPnr())
                .email(personDTO.getEmail())
                .password(personDTO.getPassword())
                .role_id(2)
                .username(personDTO.getUsername())
                .build();
        personRepository.save(person);
    }
}
