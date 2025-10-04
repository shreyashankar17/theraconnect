package com.theraconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theraconnect.model.EmergencyAlert;

public interface EmergencyAlertRepository extends JpaRepository<EmergencyAlert, Long>{
	List<EmergencyAlert> findByUser_UserId(Long userId);

}
