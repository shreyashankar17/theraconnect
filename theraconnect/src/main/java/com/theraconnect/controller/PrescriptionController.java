package com.theraconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.theraconnect.model.Prescription;
import com.theraconnect.service.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/{appointmentId}")
    public Prescription add(@PathVariable Long appointmentId, @RequestBody Prescription prescription) {
        return prescriptionService.createPrescription(appointmentId, prescription);
    }

    @GetMapping("/{appointmentId}")
    public Prescription get(@PathVariable Long appointmentId) {
        return prescriptionService.getByAppointment(appointmentId);
    }
}