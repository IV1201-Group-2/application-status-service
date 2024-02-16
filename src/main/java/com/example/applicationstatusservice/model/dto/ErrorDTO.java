package com.example.applicationstatusservice.model.dto;

import lombok.Getter;

@Getter
public class ErrorDTO {

    private final String error;

    public ErrorDTO(String error) {
        this.error = error;
    }

}
