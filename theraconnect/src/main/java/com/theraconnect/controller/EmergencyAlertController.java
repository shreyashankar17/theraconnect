package com.theraconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.theraconnect.model.EmergencyAlert;
import com.theraconnect.service.EmergencyAlertService;

import java.util.List;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyAlertController {

    @Autowired
    private EmergencyAlertService emergencyService;

    @PostMapping("/{userId}")
    public EmergencyAlert trigger(@PathVariable Long userId) {
        return emergencyService.triggerEmergency(userId);
    }

    @GetMapping("/user/{userId}")
    public List<EmergencyAlert> getAll(@PathVariable Long userId) {
        return emergencyService.getAlertsByUser(userId);
    }
}
