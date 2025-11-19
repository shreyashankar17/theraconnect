package com.theraconnect.scheduler;

import com.theraconnect.model.Appointment;
import com.theraconnect.enums.AppointmentStatus;
import com.theraconnect.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AppointmentStatusUpdater {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Scheduled(fixedRate = 60000) // runs every 60 seconds
    //  @Scheduled(fixedRate = 3600000) // runs every 1 hour (3600000 milliseconds)

    public void updateAppointmentStatuses() {
        LocalDateTime now = LocalDateTime.now();
        List<Appointment> upcomingAppointments = appointmentRepository.findByStatus(AppointmentStatus.UPCOMING);

        for (Appointment appointment : upcomingAppointments) {
            if (appointment.getSlotTime().isBefore(now)) {
                appointment.setStatus(AppointmentStatus.COMPLETED);
                appointmentRepository.save(appointment);
            }
        }
    }
}
