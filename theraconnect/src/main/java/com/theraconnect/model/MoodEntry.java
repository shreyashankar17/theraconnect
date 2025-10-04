package com.theraconnect.model;
 
import java.time.LocalDate;
import jakarta.persistence.*;
 
@Entity
@Table(name = "mood_entries")
public class MoodEntry {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private int moodLevel;
 
    @Column(name = "entry_date")
    private LocalDate entryDate;
 
 
    // ✅ Correct placement inside class
    @ManyToOne
    @JoinColumn(name = "patient_profile_id")
    private PatientProfile patientProfile;
 
    
    public MoodEntry() {
		super();
	}


	public MoodEntry(Long id, int moodLevel, LocalDate entryDate, PatientProfile patientProfile) {
		super();
		this.id = id;
		this.moodLevel = moodLevel;
		this.entryDate = entryDate;
		this.patientProfile = patientProfile;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getMoodLevel() {
		return moodLevel;
	}


	public void setMoodLevel(int moodLevel) {
		this.moodLevel = moodLevel;
	}


	public LocalDate getEntryDate() {
		return entryDate;
	}


	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}


	public PatientProfile getPatientProfile() {
		return patientProfile;
	}


	public void setPatientProfile(PatientProfile patientProfile) {
		this.patientProfile = patientProfile;
	}


	// 🔹 toString (for logging/debugging)
    @Override
    public String toString() {
        return "MoodEntry{" +
                "id=" + id +
                ", moodLevel=" + moodLevel +
                ", entryDate=" + entryDate +
                ", patientProfile=" + (patientProfile != null ? patientProfile.getPatientProfileId() : "null") +
                '}';
    }
}
 