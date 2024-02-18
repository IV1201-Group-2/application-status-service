package com.example.applicationstatusservice.model.dto;
import lombok.*;

@Getter
public class ApplicationStatusDTO {

    private final Long person_id;
    private final String status;

    public ApplicationStatusDTO(Long personId, String status) {
        person_id = personId;
        this.status = status;
    }
}
