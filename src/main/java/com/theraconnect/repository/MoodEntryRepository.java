package com.theraconnect.repository;
 
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.theraconnect.model.MoodEntry;
import com.theraconnect.model.PatientProfile;
 
public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
 
    // ✅ Spring Data JPA will auto-generate this query
    List<MoodEntry> findTop7ByPatientProfileOrderByEntryDateDesc(PatientProfile patientProfile);
 
    // ✅ Prevent duplicate entry per day
    Optional<MoodEntry> findByPatientProfileAndEntryDate(PatientProfile patientProfile, LocalDate entryDate);
}
 
 
 