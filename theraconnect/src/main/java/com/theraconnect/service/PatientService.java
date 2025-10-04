package com.theraconnect.service;

import com.theraconnect.model.PatientProfile;

public interface PatientService {
	 PatientProfile getProfileById(Long id);
	    PatientProfile updateProfile(Long id, PatientProfile profile);

}
