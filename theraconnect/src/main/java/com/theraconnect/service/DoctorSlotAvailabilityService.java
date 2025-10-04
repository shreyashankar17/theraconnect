package com.theraconnect.service;

import java.time.LocalDate;
import java.util.List;

import com.theraconnect.dto.AvailabilityCalendarDTO;
import com.theraconnect.model.DoctorSlotAvailability;

public interface DoctorSlotAvailabilityService {
	DoctorSlotAvailability addAvailability(DoctorSlotAvailability availability);

    List<DoctorSlotAvailability> getAvailabilityByDoctorAndDate(Long doctorId, LocalDate date);

    List<DoctorSlotAvailability> getAllAvailabilityForDoctor(Long doctorId);

    void deleteAvailability(Long availabilityId);
    
    DoctorSlotAvailability getAvailabilityById(Long id);

	List<AvailabilityCalendarDTO> getCalendarSlots(Long doctorId);

	DoctorSlotAvailability save(DoctorSlotAvailability slot);

}
