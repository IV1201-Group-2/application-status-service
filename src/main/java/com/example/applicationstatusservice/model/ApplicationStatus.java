package com.example.applicationstatusservice.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ApplicationStatus model representing the database structure created for each Application status.
 * {@code @Data} automatically generates getters and setters for this class.
 * {@code @NoArgsConstructor} generates default constructor to instantiate objects required by JPA
 * {@code @AllArgsConstructor} creates and initializes all fields of an object in one class.
 * {@code @Entity} indicates that the following class is an entity that maps to a table in the database.
 * {@code @Builder} creates a builder for the class for a more readable code syntax.
 *
 * @Table(name = "application_status") hardcoded the table name of this class in the database to "applicationStatus".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "application_status")
@SuppressFBWarnings("EI_EXPOSE_REP")
public class ApplicationStatus {

    /**
     * {@code @Id} marks the application_status_id as the primary key
     * {@code @GeneratedValue} sets the application_status to be automatically generated and
     * incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long application_status_id;

    /**
     * {@code @OneToOne} Indicates a one-to-one relationship between
     * the Person and Application_status table.
     * {@code @JoinColumn} Making the person_id a foreign key to application_status.
     */
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    /**
     * Status of an application which can either be Accept/Reject/Pending.
     */
    @Column(length = 7)
    private String status;
}

