package com.theraconnect.service.impl;

import com.theraconnect.model.PatientProfile;
import com.theraconnect.repository.PatientProfileRepository;
import com.theraconnect.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientProfileRepository repository;

    @Override
    public PatientProfile getProfileById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public PatientProfile updateProfile(Long id, PatientProfile updatedProfile) {
    	PatientProfile existing = repository.findById(id).orElseThrow();
        existing.setDob(updatedProfile.getDob());
        existing.setGender(updatedProfile.getGender());
        existing.setContact(updatedProfile.getContact());
        return repository.save(existing);
    
    }
}