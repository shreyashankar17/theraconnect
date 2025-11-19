package com.theraconnect.controller;
 
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
import com.theraconnect.model.MoodEntry;
import com.theraconnect.model.PatientProfile;
import com.theraconnect.repository.PatientProfileRepository;

import com.theraconnect.service.impl.MoodEntryServiceImpl;

import jakarta.servlet.http.HttpSession;
 
@Controller
@RequestMapping("/mood") // 🔹 All paths in this controller start with /mood
public class MoodController {
 
    @Autowired
    private MoodEntryServiceImpl moodEntryService;
 
    @Autowired
    private PatientProfileRepository patientProfileRepository;
 
    /**
     * ✅ Handle mood submission from /mood/view form.
     * - Saves mood to database
     * - Redirects back to /mood/view to refresh chart
     */
    @PostMapping("/log")
    public String logMood(@RequestParam int moodLevel,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
 
        Long profileId = (Long) session.getAttribute("patientProfileId");
        PatientProfile profile = patientProfileRepository.findById(profileId).orElseThrow();
 
        moodEntryService.logMood(profile, moodLevel, null); // no notes
 
        // ✅ Send flash attribute to show success message
        redirectAttributes.addFlashAttribute("justLogged", moodLevel);
        return "redirect:/mood/view";
    }
 
 
    /**
     * ✅ Show mood logging page + weekly chart
     * - Displays emoji form for mood selection
     * - Renders last 7 entries in chart
     */
 
@GetMapping("/view")
public String viewProgress(Model model, HttpSession session) {
    Long profileId = (Long) session.getAttribute("patientProfileId");
 
    if (profileId == null) {
        return "redirect:/login";
    }
 
    PatientProfile profile = patientProfileRepository.findById(profileId)
                                 .orElseThrow(() -> new IllegalArgumentException("Invalid profile ID"));
 
    List<MoodEntry> entries = moodEntryService.getRecentMoodEntries(profile);
 
    // ✅ Format dates to String for JSP
    List<String> dateLabels = entries.stream()
        .map(entry -> entry.getEntryDate().toString()) // or use .format(...) for custom formatting
        .collect(Collectors.toList());
 
    List<Integer> moodLevels = entries.stream()
        .map(MoodEntry::getMoodLevel)
        .collect(Collectors.toList());
 
    model.addAttribute("labels", dateLabels);
    model.addAttribute("moodLevels", moodLevels);
    return "view"; // maps to view.jsp
}
}
 
 