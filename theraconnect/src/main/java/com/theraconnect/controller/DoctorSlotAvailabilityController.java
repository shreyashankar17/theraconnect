

package com.theraconnect.controller;
 
import org.aspectj.weaver.patterns.ConcreteCflowPointcut.Slot;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
import com.theraconnect.dto.AvailabilityCalendarDTO;

import com.theraconnect.model.DoctorProfile;

import com.theraconnect.model.DoctorSlotAvailability;

import com.theraconnect.model.User;

import com.theraconnect.service.DoctorService;

import com.theraconnect.service.DoctorSlotAvailabilityService;

import com.theraconnect.service.UserService;
 
import jakarta.servlet.http.HttpSession;
 
import java.time.LocalDate;

import java.time.LocalDateTime;

import java.time.LocalTime;

import java.util.ArrayList;

import java.util.List;
 
@Controller

@RequestMapping("/api/availability")

public class DoctorSlotAvailabilityController {
 
    @Autowired

    private DoctorSlotAvailabilityService availabilityService;

    @Autowired    

    private UserService userService;

    @Autowired       

    private DoctorService doctorProfileService;
 
    // Add availability

    @PostMapping

    public DoctorSlotAvailability addAvailability(@RequestBody DoctorSlotAvailability availability) {

        return availabilityService.addAvailability(availability);

    }
 
    // Get availability for specific doctor on specific date

    @GetMapping("/doctor/{doctorId}/date/{date}")

    public List<DoctorSlotAvailability> getByDoctorAndDate(@PathVariable Long doctorId, @PathVariable String date) {

        LocalDate localDate = LocalDate.parse(date);

        return availabilityService.getAvailabilityByDoctorAndDate(doctorId, localDate);

    }
 
    // Get all availability for a doctor

    @GetMapping("/doctor/{doctorId}")

    public List<DoctorSlotAvailability> getAllAvailability(@PathVariable Long doctorId) {

        return availabilityService.getAllAvailabilityForDoctor(doctorId);

    }
 
    // Delete an availability slot

    @DeleteMapping("/{id}")

    public String deleteAvailability(@PathVariable Long id) {

        availabilityService.deleteAvailability(id);

        return "Slot deleted";

    }

    @GetMapping("/calendar/doctor/{doctorId}")

    public List<AvailabilityCalendarDTO> getCalendarSlots(@PathVariable Long doctorId) {

        List<DoctorSlotAvailability> slots = availabilityService.getAllAvailabilityForDoctor(doctorId);

        List<AvailabilityCalendarDTO> calendarSlots = new ArrayList<>();

        LocalDate today=LocalDate.now();
 
        for (DoctorSlotAvailability slot : slots) {

        	if(!slot.getDate().isBefore(today)) {

            LocalDateTime start = LocalDateTime.of(slot.getDate(), slot.getStartTime());

            LocalDateTime end = LocalDateTime.of(slot.getDate(), slot.getEndTime());

            calendarSlots.add(new AvailabilityCalendarDTO(slot.getAvailabilityId(), start, end));

        	}

        }
 
        return calendarSlots;

    }

    //new one

   /*

    * @GetMapping("/calendar/{doctorId}")

public List<CalendarSlotDTO> getCalendarSlots(@PathVariable Long doctorId) {

    List<DoctorAvailability> slots = availabilityService.getAllAvailabilityForDoctor(doctorId);

    return slots.stream().map(slot -> new CalendarSlotDTO(

        slot.getAvailabilityId(),

        slot.getDate().toString(),

        slot.getStartTime().toString(),

        slot.getEndTime().toString()

    )).toList();

}*/

//22july

 
	@GetMapping("/addslotform")

	public String showAvailabilityForm(@RequestParam Long doctorId, Model model) {

		DoctorProfile doctor = doctorProfileService.getDoctorById(doctorId);

		if (doctor == null) {

			throw new RuntimeException("Doctor not found");

		}
 
		model.addAttribute("doctor", doctor);

		return "addslot-availability"; // JSP file name without .jsp

	}
 
    

    //add slot from Doctr Profile

    @PostMapping("/add")

    public String addAvailabilitySlot(

            @RequestParam Long doctorId,

            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,

            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") LocalTime startTime,

            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") LocalTime endTime,

            @RequestParam boolean isBooked,

            RedirectAttributes redirectAttributes

    ) {
 
		// Fetch doctor profile

//    	 DoctorProfile doctor =( doctorProfileService.getDoctorById(doctorId))

//                .orElseThrow(() -> new RuntimeException("Doctor not found"));

 
		DoctorProfile doctor = doctorProfileService.getDoctorById(doctorId);

		if (doctor == null) {

			throw new RuntimeException("Doctor not found");

		}
 
        // Validate that the slot is not in the past

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime slotStart = LocalDateTime.of(date, startTime);

        if (slotStart.isBefore(now)) {

            redirectAttributes.addFlashAttribute("error", "Cannot add a slot in the past.");

            return "redirect:/availability/form"; // Redirect back to form page

        }
 
        // Create and save the slot

        DoctorSlotAvailability slot = new DoctorSlotAvailability(doctor, date, startTime, endTime, isBooked);

        availabilityService.save(slot);
 
        redirectAttributes.addFlashAttribute("success", "Slot added successfully.");

        return "redirect:/doctor-dashboard"; // Redirect to slot listing page

    }

    //new

    @PostMapping("/api/availability/add")

    public String processSlotForm(@ModelAttribute DoctorSlotAvailability slot, HttpSession session) {

        DoctorSlotAvailabilityService slotRepository = null;

		// ✅ Save slot logic here

        slotRepository.save(slot);
 
        // ✅ Set success flag in session

        session.setAttribute("slotAdded", true);
 
        // ✅ Redirect to dashboard

        return "doctor-dashboard";

    }
 
 
}

 