package com.theraconnect.model;


import jakarta.persistence.*;
import com.theraconnect.enums.EmergencyStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_alert")
public class EmergencyAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emergencyId;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private EmergencyStatus status;

    private LocalDateTime triggeredAt;

    public EmergencyAlert() {}

    public EmergencyAlert(User user, EmergencyStatus status, LocalDateTime triggeredAt) {
        this.user = user;
        this.status = status;
        this.triggeredAt = triggeredAt;
    }

    public Long getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(Long emergencyId) {
        this.emergencyId = emergencyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EmergencyStatus getStatus() {
        return status;
    }

    public void setStatus(EmergencyStatus status) {
        this.status = status;
    }

    public LocalDateTime getTriggeredAt() {
        return triggeredAt;
    }

    public void setTriggeredAt(LocalDateTime triggeredAt) {
        this.triggeredAt = triggeredAt;
    }
}
