package com.theraconnect.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patient_profile")
public class PatientProfile {

    @Id
    private Long patientProfileId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "patientProfileId")
    private User user;

    private Date dob;

    private String gender;

    private String contact;

    public PatientProfile() {}

    public PatientProfile(Date dob, String gender, String contact, User user) {
        this.dob = dob;
        this.gender = gender;
        this.contact = contact;
        this.user = user;
    }

    public Long getPatientProfileId() {
        return patientProfileId;
    }

    public void setPatientProfileId(Long patientProfileId) {
        this.patientProfileId = patientProfileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}




