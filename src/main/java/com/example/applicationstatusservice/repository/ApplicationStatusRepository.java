package com.example.applicationstatusservice.repository;

import com.example.applicationstatusservice.model.ApplicationStatus;
import com.example.applicationstatusservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The applicationStatusRepository is a repository that contains methods for data
 * retrieval/modification operations.
 */
@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
    /**
     * Method finding an application status based on a person.
     *
     * @param person is a person entity taken as a parameter.
     * @return an ApplicationStatus object.
     */
    ApplicationStatus findByPerson(Person person);
}
