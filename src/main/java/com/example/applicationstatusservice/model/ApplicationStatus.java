package com.example.applicationstatusservice.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "application_status")
@SuppressFBWarnings("EI_EXPOSE_REP")
public class ApplicationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long application_status_id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(length = 7)
    private String status;
}

