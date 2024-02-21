package com.example.applicationstatusservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ApplicationStatusDTO is the data transfer object containing the person_id and the status
 * required to set a status for an application.
 * {@code @Getter} provides getter functions for the ApplicationStatusDTO variables.
 * {@code @AllArgsConstructor} creates and initializes all fields of an object in one class.
 */
@Getter
@AllArgsConstructor
public class ApplicationStatusDTO {

    /**
     * Person_id represents the id of a Person entity.
     */
    private final Long person_id;
    /**
     * Status represents the status of an application that can
     * either be Accept/Pending/Reject.
     */
    private final String status;

}
