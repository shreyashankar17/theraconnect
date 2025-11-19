package com.theraconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theraconnect.model.PatientProfile;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long>{

}
