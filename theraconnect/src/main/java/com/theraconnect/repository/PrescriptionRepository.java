package com.theraconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theraconnect.model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long>{

	 Prescription findByAppointment_AppointmentId(Long appointmentId);
}
