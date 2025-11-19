package com.theraconnect.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.theraconnect.model.Appointment;

public interface AppointmentService {
	Appointment bookAppointment(Appointment appointment);
    Appointment getById(Long id);
    List<Appointment> getByDoctor(Long doctorId);
    List<Appointment> getByPatient(Long patientId);
    
  //Object getAppointmentStats();
    Map<String, Long> getAppointmentStats();
	 Map<String, Long> getStatusBreakdown();
	 List<Appointment> getFilteredAppointments(
	            String nameSearch,
	            String dateSearch,
	            String statusFilter,
	            String sortOrder);

 

}
