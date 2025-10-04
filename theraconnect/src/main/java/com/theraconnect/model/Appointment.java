package com.theraconnect.model;
 
 
import jakarta.persistence.*;
import com.theraconnect.enums.AppointmentStatus;
import java.time.LocalDateTime;
 
@Entity
@Table(name = "appointment")
public class Appointment {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
 
    @ManyToOne
    private User doctor;
 
    @ManyToOne
    private User patient;
 
    private LocalDateTime slotTime;
 
    private String meetingLink;
    private String reason;
 
    //prescription is stored in the format of image jpg.
    @Lob
    private byte[] imageData;
 
    public String getPrescriptionName() {
		return prescriptionName;
	}
 
	public void setPrescriptionName(String prescriptionName) {
		this.prescriptionName = prescriptionName;
	}
 
	private String prescriptionName;
 
    
    public Appointment(String prescriptionName) {
		super();
		this.prescriptionName = prescriptionName;
	}
 
	public Appointment(byte[] imageData) {
		super();
		this.imageData = imageData;
	}
 
	public byte[] getImageData() {
		return imageData;
	}
 
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
 
	@Enumerated(EnumType.STRING)
    private AppointmentStatus status;
 
    public Long getAppointmentId() {
		return appointmentId;
	}
 
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
 
	public User getDoctor() {
		return doctor;
	}
 
	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}
 
	public User getPatient() {
		return patient;
	}
 
	public void setPatient(User patient) {
		this.patient = patient;
	}
 
	public LocalDateTime getSlotTime() {
		return slotTime;
	}
 
	public void setSlotTime(LocalDateTime slotTime) {
		this.slotTime = slotTime;
	}
 
	public String getMeetingLink() {
		return meetingLink;
	}
 
	public void setMeetingLink(String meetingLink) {
		this.meetingLink = meetingLink;
	}
 
	public String getReason() {
		return reason;
	}
 
	public void setReason(String reason) {
		this.reason = reason;
	}
 
	public AppointmentStatus getStatus() {
		return status;
	}
 
	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}
 
	public Appointment(Long appointmentId, User doctor, User patient, LocalDateTime slotTime, String meetingLink,
			String reason, AppointmentStatus status) {
		super();
		this.appointmentId = appointmentId;
		this.doctor = doctor;
		this.patient = patient;
		this.slotTime = slotTime;
		this.meetingLink = meetingLink;
		this.reason = reason;
		this.status = status;
	}
 
	public Appointment() {}
 
    
}