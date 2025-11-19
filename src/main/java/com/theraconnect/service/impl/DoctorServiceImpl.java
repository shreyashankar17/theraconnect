package com.theraconnect.service.impl;

import com.theraconnect.model.DoctorProfile;
import com.theraconnect.repository.AppointmentRepository;
import com.theraconnect.repository.DoctorProfileRepository;
import com.theraconnect.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
	

@Autowired
    private AppointmentRepository appointmentRepository;


    @Autowired
    private DoctorProfileRepository repository;

    @Override
    public DoctorProfile getDoctorById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<DoctorProfile> getAllDoctors() {
        return repository.findAll();
    }
    
    
}