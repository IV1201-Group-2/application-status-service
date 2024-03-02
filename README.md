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

```
├─ .github
│  └─ workflows
│     └─ CI.yml                                                      - On push, runs tests, static analysis, bug checks, linting, and provides test coverage report
├─ .gitignore
├─ .mvn
│  └─ wrapper
│     ├─ maven-wrapper.jar
│     └─ maven-wrapper.properties
├─ LICENSE
├─ Procfile
├─ README.md
├─ lombok.config
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
├─ src
│  ├─ main
│  │  ├─ java
│  │  │  └─ com
│  │  │     └─ example
│  │  │        └─ applicationstatusservice
│  │  │           ├─ ApplicationStatusServiceApplication.java        - Initiates spring boot server
│  │  │           ├─ controller                                      - Handles HTTP requests
│  │  │           │  └─ ApplicationStatusController.java
│  │  │           ├─ exception                                       - Throws a custom error in case of any general issues while the register microservice is running
│  │  │           │  └─ ExceptionHandler.java
│  │  │           ├─ model                                           - Models representing the database structure created for ApplicationStatus and Person 
│  │  │           │  ├─ ApplicationStatus.java
│  │  │           │  ├─ Person.java    
│  │  │           │  └─ dto                                          - Data Transfer Objects
│  │  │           │     ├─ ApplicationStatusDTO.java
│  │  │           │     ├─ ErrorDTO.java
│  │  │           │     └─ PersonDTO.java
│  │  │           ├─ repository                                      - Repositories that contain methods for data retrieval/modification operations
│  │  │           │  ├─ ApplicationStatusRepository.java
│  │  │           │  └─ PersonRepository.java
│  │  │           ├─ security                                        - Contains configurations and security settings
│  │  │           │  ├─ SecurityConfig.java
│  │  │           │  └─ WebConfiguration.java
│  │  │           └─ service                                         - Service handling business-logic application-staus operations, person-related operations, and creating,           │  │  │                                                                authenticating, and authorizing Jwt token operations                                         
│  │  │              ├─ ApplicationStatusService.java
│  │  │              ├─ JwtAuthService.java
│  │  │              └─ PersonService.java
│  │  └─ resources                                                   - Stores configuration properties and configuration for logging
│  │     ├─ application.properties                          
│  │     └─ log4j.properties
│  └─ test                                                           - Integration tests covering all MVC layers
│     └─ java
│        └─ com
│           └─ example
│              └─ applicationstatusservice  
│                 ├─ ApplicationStatusControllerIntegrationTest.java
│                 └─ ApplicationStatusIntegrationTest.java
└─ system.properties                                                 - Required Heroku settings       
```

## Environment Variables
### Heroku config variables required by Application Status Service
1. DATABASE_URL
2. DATABASE_NAME
3. DATABASE_PASSWORD
