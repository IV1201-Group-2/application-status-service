package com.example.applicationstatusservice.controller;

import com.example.applicationstatusservice.model.dto.ApplicationStatusDTO;
import com.example.applicationstatusservice.model.dto.ErrorDTO;
import com.example.applicationstatusservice.service.ApplicationStatusService;
import com.example.applicationstatusservice.service.JwtAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ApplicationStatusController handles HTTP requests and
 * presents different JSON objects with HTTP statuses based on
 * user-input during registration.
 */
@Controller
public class ApplicationStatusController {

    /**
     * Logger to log events in the Controller layer.
     */
    private static final Logger logger = LogManager.getLogger(ApplicationStatusController.class);


    /**
     * An instance of ApplicationStatusService handling business
     * logic specific to status-related operations.
     */
    private final ApplicationStatusService applicationStatusService;

    /**
     * An instance of ApplicationStatusService handling
     * logic specific to creating, authentication and authorization JWT tokens.
     */
    private final JwtAuthService jwtAuthService;

    /**
     * The constructor for ApplicationStatusController.
     *
     * @param applicationStatusService is the service responsible for the business logic
     *                                 specific to status-related operations.
     * @param jwtAuthService           is the service responsible for creating, authentication and
     *                                 authorization of JWT tokens.
     */
    public ApplicationStatusController(ApplicationStatusService applicationStatusService, JwtAuthService jwtAuthService) {
        this.applicationStatusService = applicationStatusService;
        this.jwtAuthService = jwtAuthService;
    }

    /**
     * Method containing validation method calls and returns different
     * JSON objects with corresponding HTTP
     * statuses depending on user input.
     *
     * @param applicationStatusDTO Data transfer object representing status submitted information.
     *                             {@code @RequestBody} Provides the data embedded in the HTTP request.
     * @return HTTP status and an empty LinkedMultiValueMap as the body.
     */
    @PostMapping(value = "/api/applicant", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> handleApplicationStatus(@RequestHeader("Authorization") String header, @RequestBody ApplicationStatusDTO applicationStatusDTO, HttpServletRequest request) {
        //IP address of the machine requesting to set/update application status.
        String IP = request.getRemoteAddr();

        //Error messages in case of an invalid person_id or an invalid status or an invalid JWT token.
        String jwtTokenErrorMessage = jwtAuthService.jwtAuth(header);
        String personIdErrorMessage = applicationStatusService.isPersonIdValid(applicationStatusDTO.getPerson_id());
        String statusErrorMessage = applicationStatusService.isStatusValid(applicationStatusDTO.getStatus());

        //Validation process to make sure person_id and status received is correct.
        if ("UNAUTHORIZED".equals(jwtTokenErrorMessage)) {
            logger.warn("The person with IP address: {} has unauthorized access with the provided JWT token ", IP);
            return new ResponseEntity<>(new ErrorDTO(jwtTokenErrorMessage), HttpStatus.BAD_REQUEST);
        } else if ("INVALID_DATA".equals(personIdErrorMessage)) {
            logger.warn("The person with IP address: {} submitted an invalid person Id: {} ", IP, applicationStatusDTO.getPerson_id());
            return new ResponseEntity<>(new ErrorDTO(personIdErrorMessage), HttpStatus.BAD_REQUEST);
        } else if ("INVALID_DATA".equals(statusErrorMessage)) {
            logger.warn("The person with IP address: {} submitted an invalid status: {} ", IP, applicationStatusDTO.getStatus());
            return new ResponseEntity<>(new ErrorDTO(statusErrorMessage), HttpStatus.BAD_REQUEST);
        }

        //Inserts or updates the status of an application through the service-layer.
        applicationStatusService.updateApplicationStatus(applicationStatusDTO);
        logger.info("The person with the IP address: {} has updated the application for person Id: {} with the status: {} ", IP, applicationStatusDTO.getPerson_id(), applicationStatusDTO.getStatus());
        return new ResponseEntity<>(new LinkedMultiValueMap<>(), HttpStatus.OK);
    }
}
