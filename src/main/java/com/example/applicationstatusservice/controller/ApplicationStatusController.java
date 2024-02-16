package com.example.applicationstatusservice.controller;

import com.example.applicationstatusservice.model.dto.ApplicationStatusDTO;
import com.example.applicationstatusservice.model.dto.ErrorDTO;
import com.example.applicationstatusservice.service.ApplicationStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationStatusController {
    
    private final ApplicationStatusService applicationStatusService;

    public ApplicationStatusController(ApplicationStatusService applicationStatusService) {
        this.applicationStatusService = applicationStatusService;
    }

    @GetMapping("/api/applicant")
    public String applicationPage() {
        return "application";
    }

    @PostMapping(value = "/api/applicant", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> handleApplicationStatus(@RequestBody ApplicationStatusDTO applicationStatusDTO) {
        String personIdErrorMessage = applicationStatusService.isPersonIdValid(applicationStatusDTO.getPerson_id());
        String statusErrorMessage = applicationStatusService.isStatusValid(applicationStatusDTO.getStatus());
        
        if("INVALID_DATA".equals(personIdErrorMessage)){
            return new ResponseEntity<>(new ErrorDTO(personIdErrorMessage), HttpStatus.BAD_REQUEST);
        } else if ("INVALID_DATA".equals(statusErrorMessage)) {
            return new ResponseEntity<>(new ErrorDTO(statusErrorMessage), HttpStatus.BAD_REQUEST);
        }

        applicationStatusService.updateApplicationStatus(applicationStatusDTO);
        return new ResponseEntity<>(new LinkedMultiValueMap<>(), HttpStatus.OK);
    }

}
