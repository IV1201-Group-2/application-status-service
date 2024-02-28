package com.example.applicationstatusservice.service;

import com.example.applicationstatusservice.model.ApplicationStatus;
import com.example.applicationstatusservice.model.Person;
import com.example.applicationstatusservice.model.dto.ApplicationStatusDTO;
import com.example.applicationstatusservice.repository.ApplicationStatusRepository;
import com.example.applicationstatusservice.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ApplicationStatusService is a service class meant to handle the business-logic
 * specific to status-related operations.
 */
@Service
public class ApplicationStatusService {

    /**
     * Logger to log events passing the business-logic for application status insertion/update.
     */
    private static final Logger logger = LogManager.getLogger(ApplicationStatusService.class);

    /**
     * An instance of applicationStatusRepository used for data access/retrieval in the Application_status table.
     */
    private final ApplicationStatusRepository applicationStatusRepository;
    /**
     * An instance of personRepository used for data access/retrieval in the Person table.
     */
    private final PersonRepository personRepository;

    /**
     * Constructor for the ApplicationStatusService class.
     * {@code @Autowired} provides automatic dependency injection.
     *
     * @param applicationStatusRepository handles handles data retrieval/modification
     *                                    operations in the Application_status table.
     * @param personRepository            handles handles data retrieval/modification operations
     *                                    in the Person table.
     */
    @Autowired
    public ApplicationStatusService(ApplicationStatusRepository applicationStatusRepository, PersonRepository personRepository) {
        this.applicationStatusRepository = applicationStatusRepository;
        this.personRepository = personRepository;
    }

    /**
     * Method for either inserting a status for an application or
     * updating a previously set status of an application.
     * {@code @Transactional} ensures application is saved to the
     * database only if the transaction is successful.
     *
     * @param applicationStatusDTO Data transfer object
     *                             representing status for an application.
     */
    @Transactional
    public void updateApplicationStatus(ApplicationStatusDTO applicationStatusDTO) {
        Long personId = applicationStatusDTO.getPerson_id();
        String status = applicationStatusDTO.getStatus();

        Person person = personRepository.findById(personId).orElse(null);
        ApplicationStatus checkApplicationStatus = applicationStatusRepository.findByPerson(person);
        if (checkApplicationStatus != null) {
            checkApplicationStatus.setStatus(status);
            applicationStatusRepository.save(checkApplicationStatus);
            logger.info("The status of the application for person Id: {} has been updated to status: {} ", applicationStatusDTO.getPerson_id(), applicationStatusDTO.getStatus());
        } else if (person != null) {
            ApplicationStatus applicationStatus = ApplicationStatus.builder().person(person).status(applicationStatusDTO.getStatus()).build();
            applicationStatusRepository.save(applicationStatus);
            logger.info("A new application status for person Id: {} has been set to status: {} ", applicationStatusDTO.getPerson_id(), applicationStatusDTO.getStatus());
        }
    }

    /**
     * Method to check id a person entity exists based on a person_id received in
     * the ApplicationStatusDTO.
     *
     * @param personId is the person_id provided as parameter.
     * @return a response string indicating either a valid or an invalid person_id.
     */
    public String isPersonIdValid(Long personId) {
        boolean isPersonIdValid = personRepository.existsById(personId);
        logger.debug("Check if person Id: {} exists: {} ", personId, isPersonIdValid);
        if (isPersonIdValid) {
            logger.info("Person Id: {} exists ", personId);
            return "VALID_DATA";
        }
        logger.error("Person Id: {} does not exists ", personId);
        return "INVALID_DATA";
    }

    /**
     * Method to check if the status received through the ApplicationStatusDTO is
     * correct and with a correct format.
     *
     * @param status is the applications status provided as parameter.
     * @return a response string indicating either a valid or an invalid status.
     */
    public String isStatusValid(String status) {
        logger.info("Check to see if status: {} is valid", status);
        return switch (status) {
            case "Accept", "Reject", "Pending" -> "VALID_DATA";
            default -> "INVALID_DATA";
        };
    }
}
