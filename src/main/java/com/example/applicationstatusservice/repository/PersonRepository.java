package com.example.applicationstatusservice.repository;

import com.example.applicationstatusservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The personRepository is a repository that contains methods for data
 * retrieval/modification operations.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    /**
     * Retrieves the username from the database.
     *
     * @param Username represents the username that is being
     *                 searched for in the database.
     * @return Object of the type Person class.
     */
    Person findByUsername(String Username);
}