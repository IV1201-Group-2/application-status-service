# Application Status Service

## General info
This repository contains all files relevant to the backend Application Status Service:
* Service written in Spring boot application handles backend application status insertion/update and validation process.
* Contains Integration tests validating all MVC architectural layers.
* Contains log configuration file (log4j.properties)

## Recommended IDE Setup
[IntelliJ IDEA](https://www.jetbrains.com/idea/)

## Project Setup

### Perform compiling, test runs, and packaging
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
      - CI.yml -> On push, runs tests, static analysis, bug checks, linting, and provides test coverage report
  - src
    - main
      - java
        - com.example.applicationstatusservice
              - ApplicationStatusServiceApplication.java -> Initiates spring boot server
        - controller -> Handles HTTP requests
          - ApplicationStatusController 
        - exception - -> Throws a custom error in case of any general issues while the register microservice is running
          - ExceptionHandler 
        - model -> Models representing the database structure created for ApplicationStatus and Person 
          - dto -> Data Transfer Objects
            - ApplicationStatusDTO 
            - ErrorDTO 
            - PersonDTO 
          - ApplicationStatus 
          - Person
        - repository -> Repositories that contain methods for data retrieval/modification operations
          - ApplicationStatusRepository
          - PersonRepository 
        - security -> Contains configurations and security settings
          - SecurityConfig 
          - WebConfiguration
        - service -> Service handling business-logic application-staus operations, person-related operations, and creating, authenticating, and authorizing Jwt token operations 
          - ApplicationStatusService 
          - JwtAuthService 
          - PersonService 
   - resources
     - templates
       - application.properties -> Stores configuration properties
       - log4j.properties -> Configuration for logging
  - test
    - java
      - com.example.applicationstatusservice ->  Integration tests covering all MVC layers
        - ApplicationStatusControllerIntegrationTest 
        - ApplicationStatusIntegrationTest 
- procfile -> Process types 
- system.properties -> Required Heroku settings
- mwnw -> Build tool
- ...

## Environment Variables
### Heroku config variables required by Application Status Service
1. DATABASE_URL
2. DATABASE_NAME
3. DATABASE_PASSWORD
