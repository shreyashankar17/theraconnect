package com.theraconnect.model;


import jakarta.persistence.*;

@Entity
@Table(name = "doctor_profile")
public class DoctorProfile {

    @Id
    private Long doctorProfileId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "doctorProfileId")
    private User user;

    private String specialization;

    private String bio;

    private String experience;

    public DoctorProfile() {}

    public DoctorProfile(String specialization, String bio, String experience, User user) {
        this.specialization = specialization;
        this.bio = bio;
        this.experience = experience;
        this.user = user;
    }

    public Long getDoctorProfileId() {
        return doctorProfileId;
    }

    public void setDoctorProfileId(Long doctorProfileId) {
        this.doctorProfileId = doctorProfileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}

