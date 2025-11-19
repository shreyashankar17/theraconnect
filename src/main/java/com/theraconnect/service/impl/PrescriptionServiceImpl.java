package com.theraconnect.service.impl;

import com.theraconnect.model.Appointment;
import com.theraconnect.model.Prescription;
import com.theraconnect.repository.AppointmentRepository;
import com.theraconnect.repository.PrescriptionRepository;
import com.theraconnect.service.PrescriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Prescription createPrescription(Long appointmentId, Prescription prescription) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null) {
            prescription.setAppointment(appointment);
            return prescriptionRepository.save(prescription);
        }
        return null;
    }

    @Override
    public Prescription getByAppointment(Long appointmentId) {
        return prescriptionRepository.findByAppointment_AppointmentId(appointmentId);
    }
}