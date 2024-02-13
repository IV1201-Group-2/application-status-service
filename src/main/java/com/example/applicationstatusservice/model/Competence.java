package com.example.applicationstatusservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Person model representing the database structure created for each registered user.
 * {@code @Data} automatically generates getters and setters for this class.
 * {@code @NoArgsConstructor} generates default constructor to instantiate objects required by JPA
 * {@code @AllArgsConstructor} creates and initializes all fields of an object in one class.
 * {@code @Entity} indicates that the following class is an entity that maps to a table in the database.
 * {@code @Builder} creates a builder for the class for a more readable code syntax.
 *
 * @Table(name = "competence") hardcoded the table name of this class in the database to "person".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "competence")
public class Competence {

    /**
     * {@code @Id} marks the competence_id as the primary key
     * {@code @GeneratedValue} sets the competence_id to be automatically generated and
     * incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long competence_id;

    /**
     * {@code @Column} sets a length for the field "name".
     * The name of the competence type.
     */
    @Column(length = 255)
    private String name;

}