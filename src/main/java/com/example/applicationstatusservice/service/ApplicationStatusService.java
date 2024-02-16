package com.example.applicationstatusservice.service;

import com.example.applicationstatusservice.model.ApplicationStatus;
import com.example.applicationstatusservice.model.Person;
import com.example.applicationstatusservice.model.dto.ApplicationStatusDTO;
import com.example.applicationstatusservice.repository.ApplicationStatusRepository;
import com.example.applicationstatusservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ApplicationStatusService {


    private final ApplicationStatusRepository applicationStatusRepository;
    private final PersonRepository personRepository;

    @Autowired
    public ApplicationStatusService(ApplicationStatusRepository applicationStatusRepository, PersonRepository personRepository) {
        this.applicationStatusRepository = applicationStatusRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public void updateApplicationStatus(ApplicationStatusDTO applicationStatusDTO){
        Long personId = applicationStatusDTO.getPerson_id();
        Person person = new Person(personId);
        ApplicationStatus applicationStatus = ApplicationStatus.builder().person(person).status(applicationStatusDTO.getStatus()).build();
        applicationStatusRepository.save(applicationStatus);
    }

    public String isPersonIdValid(Long personId){
         if(!personRepository.existsById(personId)){
             return "INVALID_DATA";
         }
         return "VALID_DATA";
    }

    public String isStatusValid(String status){
        if(!(status.equals("Accept") || status.equals("Reject") || status.equals("Pending"))){
            return "INVALID_DATA";
        }
        return "VALID_DATA";
    }


}
