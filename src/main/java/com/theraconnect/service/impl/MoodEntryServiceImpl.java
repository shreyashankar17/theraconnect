package com.theraconnect.service.impl;
 
 
 
import com.theraconnect.model.MoodEntry;
import com.theraconnect.model.PatientProfile;
import com.theraconnect.repository.MoodEntryRepository;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
 
@Service
public class MoodEntryServiceImpl {
	
 
	@Autowired
    private final MoodEntryRepository moodEntryRepository;
 
    
    public MoodEntryServiceImpl(MoodEntryRepository moodEntryRepository) {
        this.moodEntryRepository = moodEntryRepository;
    }
 
    /**
     * ✅ Log today's mood entry for the patient.
     * Returns false if mood already logged for today.
     */
    public boolean logMood(PatientProfile profile, int moodLevel, String notes) {
        if (profile == null) {
            throw new IllegalArgumentException("Patient profile cannot be null");
        }
 
        Optional<MoodEntry> existingEntry = moodEntryRepository
                .findByPatientProfileAndEntryDate(profile, LocalDate.now());
 
        if (existingEntry.isPresent()) {
            return false; // Already logged for today
        }
 
        MoodEntry entry = new MoodEntry();
        entry.setMoodLevel(moodLevel);
        entry.setEntryDate(LocalDate.now());
        entry.setPatientProfile(profile);
 
        moodEntryRepository.save(entry);
        return true;
    }
 
    /**
     * ✅ Retrieve the latest 7 mood entries for a patient.
     * Used in /mood/view chart and summary.
     */
    public List<MoodEntry> getRecentMoodEntries(PatientProfile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Patient profile is required");
        }
 
        // 1. Fetch newest-to-oldest (DESC)
        List<MoodEntry> latestEntries = moodEntryRepository
            .findTop7ByPatientProfileOrderByEntryDateDesc(profile);
 
        // 2. Reverse to show oldest-to-newest (ASC for graph)
        Collections.reverse(latestEntries);
 
        return latestEntries;
    }
    /**
     * ✅ Fetch today's mood entry for the patient (if any).
     */
    public Optional<MoodEntry> getTodayMoodEntry(PatientProfile profile) {
        if (profile == null) {
            return Optional.empty();
        }
 
        return moodEntryRepository.findByPatientProfileAndEntryDate(profile, LocalDate.now());
    }
 
    /**
     * ✅ Delete a mood entry — optional for therapist or admin view.
     */
    public void deleteEntry(Long entryId) {
        if (entryId != null) {
            moodEntryRepository.deleteById(entryId);
        }
    }
}
 
 