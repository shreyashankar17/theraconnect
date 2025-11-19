package com.theraconnect.service.impl;
 
import com.theraconnect.enums.AppointmentStatus;
import com.theraconnect.model.Appointment;
import com.theraconnect.repository.AppointmentRepository;
import com.theraconnect.service.AppointmentService;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
 
@Service
public class AppointmentServiceImpl implements AppointmentService {
 
    @Autowired
    private AppointmentRepository repository;
 
    @Override
    public Appointment bookAppointment(Appointment appointment) {
        return repository.save(appointment);
    }
 
    @Override
    public Appointment getById(Long id) {
        return repository.findById(id).orElse(null);
    }
 
    @Override
    public List<Appointment> getByDoctor(Long doctorId) {
        return repository.findByDoctor_UserId(doctorId);
    }
 
    @Override
    public List<Appointment> getByPatient(Long patientId) {
        return repository.findByPatient_UserId(patientId);
    }
    public Map<String, Long> getAppointmentStats() {
        Map<String, Long> stats = new HashMap<>();
 
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDateTime next24h = LocalDateTime.now().plusHours(24);
 
        stats.put("today", repository.countBySlotTimeBetween(today.atStartOfDay(), today.plusDays(1).atStartOfDay()));
        stats.put("week", repository.countBySlotTimeBetween(weekStart.atStartOfDay(), today.plusDays(1).atStartOfDay()));
        stats.put("month", repository.countBySlotTimeBetween(monthStart.atStartOfDay(), today.plusDays(1).atStartOfDay()));
        stats.put("upcoming", repository.countBySlotTimeBetween(LocalDateTime.now(), LocalDateTime.now().plusHours(24)));
 
        repository.countBySlotTimeBetween(monthStart.atStartOfDay(), today.plusDays(1).atStartOfDay());
 
        return stats;
    }
    public Map<String, Long> getStatusBreakdown() {
        Map<String, Long> counts = new HashMap<>();
        for (AppointmentStatus status : AppointmentStatus.values()) {
            counts.put(status.name().toLowerCase(), repository.countByStatus(status));
        }
        return counts;
    }
 
    public List<Appointment> getFilteredAppointments(
            String nameSearch,
            String dateSearch,
            String statusFilter,
            String sortOrder) {
 
        // Fallback: fetch all
        List<Appointment> all = repository.findAll();
 
       // Stream<Appointment> stream = all.stream();
        Stream<Appointment> stream = repository.findAll().stream();

        if (nameSearch != null && !nameSearch.trim().isEmpty()) {
            stream = stream.filter(a ->
                a.getPatient().getName().toLowerCase().contains(nameSearch.toLowerCase()) ||
                a.getDoctor().getName().toLowerCase().contains(nameSearch.toLowerCase()));
        }
 
        if ("COMPLETED".equalsIgnoreCase(statusFilter)) {
            stream = stream.filter(a -> a.getStatus() == AppointmentStatus.COMPLETED);
        } else if ("UPCOMING".equalsIgnoreCase(statusFilter)) {
            stream = stream.filter(a -> a.getStatus() == AppointmentStatus.UPCOMING);
        }
 
        // Sorting
        if ("asc".equalsIgnoreCase(sortOrder)) {
            stream = stream.sorted(Comparator.comparing(Appointment::getSlotTime));
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            stream = stream.sorted(Comparator.comparing(Appointment::getSlotTime).reversed());
        }
 
 
        if (dateSearch != null && !dateSearch.trim().isEmpty()) {
            LocalDate date = LocalDate.parse(dateSearch);
            stream = stream.filter(a -> a.getSlotTime().toLocalDate().equals(date));
 
        }
 
        if (statusFilter != null && !statusFilter.trim().isEmpty()) {
            AppointmentStatus status = AppointmentStatus.valueOf(statusFilter.toUpperCase());
            stream = stream.filter(a -> a.getStatus() == status);
        }
 
        return stream.collect(Collectors.toList());
    }
 
 
    public long getAppointmentCountByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return repository.countBySlotTimeBetween(startOfDay, endOfDay);
    }
 
	
 
}