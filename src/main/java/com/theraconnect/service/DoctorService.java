package com.theraconnect.service;

import java.util.List;

import com.theraconnect.model.DoctorProfile;

public interface DoctorService {
	  DoctorProfile getDoctorById(Long id);
	    List<DoctorProfile> getAllDoctors();

}
