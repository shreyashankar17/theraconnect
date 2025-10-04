package com.theraconnect.controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.theraconnect.model.MoodEntry;
import com.theraconnect.model.PatientProfile;
import com.theraconnect.repository.PatientProfileRepository;
import com.theraconnect.service.impl.MoodEntryServiceImpl;

import jakarta.servlet.http.HttpSession;
 
@RestController
@RequestMapping("/api/mood")
public class MoodApiController {
 
    @Autowired
    private MoodEntryServiceImpl moodEntryService;
 
    @Autowired
    private PatientProfileRepository patientProfileRepository;
 
    @GetMapping("/data")
    public Map<String, Object> getMoodData(HttpSession session) {
        Long profileId = (Long) session.getAttribute("patientProfileId");
        PatientProfile profile = patientProfileRepository.findById(profileId).orElseThrow();
 
        List<MoodEntry> entries = moodEntryService.getRecentMoodEntries(profile);
        
        List<String> labels = entries.stream()
            .map(entry -> entry.getEntryDate().toString())
            .collect(Collectors.toList());
 
        List<Integer> levels = entries.stream()
            .map(MoodEntry::getMoodLevel)
            .collect(Collectors.toList());
 
        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("levels", levels);
        return response;
    }
}
 
 