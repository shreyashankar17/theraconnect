package com.theraconnect.service.impl;

import com.theraconnect.dto.AvailabilityCalendarDTO;
import com.theraconnect.model.DoctorSlotAvailability;
import com.theraconnect.repository.DoctorSlotAvailabilityRepository;
import com.theraconnect.service.DoctorSlotAvailabilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class  DoctorSlotAvailabilityServiceImpl implements DoctorSlotAvailabilityService {

    @Autowired
    private DoctorSlotAvailabilityRepository doctorAvailabilityRepository;

    @Override
    public DoctorSlotAvailability addAvailability(DoctorSlotAvailability availability) {
        List<DoctorSlotAvailability> existingSlots = doctorAvailabilityRepository
            .findByDoctor_DoctorProfileIdAndDate(availability.getDoctor().getDoctorProfileId(), availability.getDate());

        boolean isOverlapping = existingSlots.stream().anyMatch(slot ->
            !(availability.getEndTime().isBefore(slot.getStartTime()) ||
              availability.getStartTime().isAfter(slot.getEndTime()))
        );

        if (isOverlapping) {
            throw new IllegalArgumentException("Slot overlaps with an existing one.");
        }

        return doctorAvailabilityRepository.save(availability);
    }

    @Override
    public List<DoctorSlotAvailability> getAvailabilityByDoctorAndDate(Long doctorId, LocalDate date) {
        return doctorAvailabilityRepository.findByDoctor_DoctorProfileIdAndDate(doctorId, date);
    }

    @Override
    public List<DoctorSlotAvailability> getAllAvailabilityForDoctor(Long doctorId) {
        return doctorAvailabilityRepository.findByDoctor_DoctorProfileId(doctorId);
    }

    @Override
    public void deleteAvailability(Long availabilityId) {
        doctorAvailabilityRepository.deleteById(availabilityId);
    }
    
    
    
    
    
    
    //new One 
    
    @Override
	public List<AvailabilityCalendarDTO> getCalendarSlots(Long doctorId) {
		List<DoctorSlotAvailability> slots = getAllAvailabilityForDoctor(doctorId);
		List<AvailabilityCalendarDTO> calendarSlots = new ArrayList<>();
		LocalDate today = LocalDate.now();

		for (DoctorSlotAvailability slot : slots) {
			if (!slot.getDate().isBefore(today)) {
				LocalDateTime start = LocalDateTime.of(slot.getDate(), slot.getStartTime());
				LocalDateTime end = LocalDateTime.of(slot.getDate(), slot.getEndTime());
				calendarSlots.add(new AvailabilityCalendarDTO(slot.getAvailabilityId(), start, end));
			}
		}

		return calendarSlots;
	}

    
    
    
	@Override
	public DoctorSlotAvailability getAvailabilityById(Long id) {
		return doctorAvailabilityRepository.findById(id).orElseThrow(() -> new RuntimeException("Slot not found with ID "+id));

	}

	@Override
	public DoctorSlotAvailability save(DoctorSlotAvailability slot) {
		return doctorAvailabilityRepository.save(slot);
		
	}
    
    
    
    

    
}