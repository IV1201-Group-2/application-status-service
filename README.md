# Application Status Service

## Table of Contents
* [General Info](#general-info)
* [Project Setup](#project-setup)
* [Recommended IDE Setup](#recommended-ide-setup)
* [File and Directory Semantics](#file-and-directory-semantics)
* [Environment Variables](#environment-variables)

## General info
This repository contains all files relevant to the backend Application Status Service:
* Service written in Spring boot application handles backend application status insertion/update and validation process.
* Contains Integration tests validating all MVC architectural layers.
* Contains log configuration file (log4j.properties)

## Recommended IDE Setup
[IntelliJ IDEA](https://www.jetbrains.com/idea/)

## Project Setup

### Perform compiling, test runs and packaging
```sh
mvn clean install
```

### Run spring boot application 

```sh
mvn spring-boot:run
```

### Simulate how Heroku containerizes and launches the app with the [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli)
```sh
heroku local
```

### Checkstyle linting
```sh
mvn checkstyle:check
```

### Spotbugs bug analysis

```sh
mvn spotbugs:check
```

## File and Directory Semantics
- application-status-service
  - .github
    - workflows
      - CI.yml -> On push, runs tests, static analysis, bug checks, linting and provides test coverage report
  - src
    - main
      - java
        - com.example.applicationstatusservice
              - ApplicationStatusServiceApplication.java -> Initiates spring boot server
        - controller
          - ApplicationStatusController -> Handles HTTP requests
        - exception
          - ExceptionHandler -> Throws a custom error in case of any general issues while the register microservice is running
        - model
          - dto
            - ApplicationStatusDTO ->
            - ErrorDTO -> Data transfer object presents various errors depending on the validation check
            - PersonDTO -> Data transfer object containing person information required for registration
          - ApplicationStatus -> Model representing the database structure created for each application status
          - Person -> Model representing the database structure created for each registered user
        - repository
          - ApplicationStatusRepository -> Repository that contains methods for data retrieval/modification operations specific to application status
          - PersonRepository -> Repository that contains methods for data retrieval/modification operations specific to registration
        - security
          - SecurityConfig -> Contains configurations and security settings
          - WebConfiguration -> Configures mappings to allow Cross-origin requests
        - service
          - ApplicationStatusService -> Service handling business-logic specific to application-staus-related operations
          - JwtAuthService -> Service handling logic specific to creating, authenticatin and authorizing Jwt tokens
          - PersonService -> Service handling business-logic specific to person-related operations
   - resources
     - templates
       - application.properties -> Stores configuration properties
       - log4j.properties -> Configuration for logging
  - test
    - java
      - com.example.applicationstatusservice
        - ApplicationStatusControllerIntegrationTest -> Integration tests covering the controller layer 
        - ApplicationStatusIntegrationTest -> Integration tests covering the service layer and repository layer
- procfile -> Process types 
- system.properties -> Required Heroku settings
- mwnw -> Build tool
- ...

## Environment Variables
### Heroku config variables required by Application Status Service
1. DATABASE_URL
2. DATABASE_NAME
3. DATABASE_PASSWORD
