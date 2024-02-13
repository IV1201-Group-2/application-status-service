package com.example.applicationstatusservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Person model representing the database structure created for each registered user.
 * {@code @Data} automatically generates getters and setters for this class.
 * {@code @NoArgsConstructor} generates default constructor to instantiate objects required by JPA
 * {@code @AllArgsConstructor} creates and initializes all fields of an object in one class.
 * {@code @Entity} indicates that the following class is an entity that maps to a table in the database.
 * {@code @Builder} creates a builder for the class for a more readable code syntax.
 *
 * @Table(name = "availability") hardcoded the table name of this class in the database to "person".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "availability")
public class Availability {

    /**
     * {@code @Id} marks the availability_id as the primary key
     * {@code @GeneratedValue} sets the availability_id to be automatically generated and
     * incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availability_id;

    /**
     * The first name of the registering person.
     */
    private Long person_id;

    /**
     * Starting availability period of the applicant from_date
     */
    private LocalDate from_date;

    /**
     * Ending availability period of the applicant to_date
     */
    private LocalDate to_date;

}
