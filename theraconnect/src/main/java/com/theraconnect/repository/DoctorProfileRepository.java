package com.theraconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theraconnect.model.DoctorProfile;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long>{

}
