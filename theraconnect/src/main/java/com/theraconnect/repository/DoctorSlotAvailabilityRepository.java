package com.theraconnect.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theraconnect.model.DoctorSlotAvailability;

public interface DoctorSlotAvailabilityRepository extends JpaRepository<DoctorSlotAvailability, Long> {
	List<DoctorSlotAvailability> findByDoctor_DoctorProfileIdAndDate(Long doctorId, LocalDate date);

	List<DoctorSlotAvailability> findByDoctor_DoctorProfileId(Long doctorId);
	List<DoctorSlotAvailability> findByDateBefore(LocalDate date);

}
