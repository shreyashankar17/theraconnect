package com.theraconnect.service;

import com.theraconnect.model.Prescription;

public interface PrescriptionService {
	 Prescription createPrescription(Long appointmentId, Prescription prescription);
	 Prescription getByAppointment(Long appointmentId);

}
