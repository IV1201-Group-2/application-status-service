package com.example.applicationstatusservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationStatusDTO {

    private final Long person_id;
    private final String status;

}
