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

/**
 * ApplicationStatusController handles HTTP requests and
 * presents different JSON objects with HTTP statuses based on
 * user-input during registration.
 */
@Controller
public class ApplicationStatusController {

    /**
     * An instance of ApplicationStatusService handling business
     * logic specific to status-related operations.
     */
    private final ApplicationStatusService applicationStatusService;

    /**
     * The constructor for ApplicationStatusController.
     *
     * @param applicationStatusService is the service responsible for the business logic
     *                                 specific to status-related operations.
     */
    public ApplicationStatusController(ApplicationStatusService applicationStatusService) {
        this.applicationStatusService = applicationStatusService;
    }

    /**
     * Method renders the registration HTML form.
     *
     * @return Renders the name of the HTMl page for the user.
     */
    @GetMapping("/api/applicant")
    public String applicationPage() {
        return "applicant";
    }

    /**
     * Method containing validation method calls and returns different
     * JSON objects with corresponding HTTP
     * statuses depending on user input.
     *
     * @param applicationStatusDTO Data transfer object representing status submitted information.
     *                             {@code @RequestBody} Provides the data embedded in the HTTP request.
     * @return HTTP status and no header.
     */
    @PostMapping(value = "/api/applicant", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> handleApplicationStatus(@RequestBody ApplicationStatusDTO applicationStatusDTO) {
        //Error messages in case of an invalid person_id or an invalid status.
        String personIdErrorMessage = applicationStatusService.isPersonIdValid(applicationStatusDTO.getPerson_id());
        String statusErrorMessage = applicationStatusService.isStatusValid(applicationStatusDTO.getStatus());

        //Validation process to make sure person_id and status received is correct.
        if ("INVALID_DATA".equals(personIdErrorMessage)) {
            return new ResponseEntity<>(new ErrorDTO(personIdErrorMessage), HttpStatus.BAD_REQUEST);
        } else if ("INVALID_DATA".equals(statusErrorMessage)) {
            return new ResponseEntity<>(new ErrorDTO(statusErrorMessage), HttpStatus.BAD_REQUEST);
        }

        //Inserts or updates the status of an application through the service-layer.
        applicationStatusService.updateApplicationStatus(applicationStatusDTO);
        return new ResponseEntity<>(new LinkedMultiValueMap<>(), HttpStatus.OK);
    }

}
