package com.example.applicationstatusservice.repository;

import com.example.applicationstatusservice.model.Person;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The personRepository is a repository that contains methods for data
 * retrieval/modification operations.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}