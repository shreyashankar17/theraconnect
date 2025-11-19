package com.theraconnect.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.theraconnect.enums.AppointmentStatus;
import com.theraconnect.model.Appointment;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	 List<Appointment> findByDoctor_UserId(Long doctorId);
	   List<Appointment> findByPatient_UserId(Long patientId);
	   List<Appointment> findByStatus(AppointmentStatus status);
	   
	// ✅ Correct field name
	   long countBySlotTimeAfter(LocalDateTime slotTime);
 
	   long countBySlotTimeBetween(LocalDateTime start, LocalDateTime end);
 
	   @Query("SELECT COUNT(a) FROM Appointment a WHERE a.slotTime >= :start AND a.slotTime < :end")
	   long countBySlotTimeRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
 
	   long countByStatus(AppointmentStatus status);

	  

}
