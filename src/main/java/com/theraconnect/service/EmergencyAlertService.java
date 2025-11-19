package com.theraconnect.service;

import java.util.List;

import com.theraconnect.model.EmergencyAlert;

public interface EmergencyAlertService {
	EmergencyAlert triggerEmergency(Long userId);
    List<EmergencyAlert> getAlertsByUser(Long userId);

}
