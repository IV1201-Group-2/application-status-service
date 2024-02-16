package com.example.applicationstatusservice.repository;

import com.example.applicationstatusservice.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {

}
