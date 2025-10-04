package com.theraconnect.service.impl;

import com.theraconnect.enums.EmergencyStatus;
import com.theraconnect.model.EmergencyAlert;
import com.theraconnect.model.User;
import com.theraconnect.repository.EmergencyAlertRepository;
import com.theraconnect.repository.UserRepository;
import com.theraconnect.service.EmergencyAlertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmergencyAlertServiceImpl implements EmergencyAlertService {

    @Autowired
    private EmergencyAlertRepository emergencyAlertRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public EmergencyAlert triggerEmergency(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            EmergencyAlert alert = new EmergencyAlert();
            alert.setUser(user);
            alert.setTriggeredAt(LocalDateTime.now());
            alert.setStatus(EmergencyStatus.PENDING);
            return emergencyAlertRepository.save(alert);
        }
        return null;
    }

    @Override
    public List<EmergencyAlert> getAlertsByUser(Long userId) {
        return emergencyAlertRepository.findByUser_UserId(userId);
    }
}