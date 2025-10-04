package com.theraconnect.service;
 
import java.math.BigDecimal;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.theraconnect.enums.Role;
import com.theraconnect.repository.AppointmentRepository;
import com.theraconnect.repository.PaymentRepository;
import com.theraconnect.repository.UserRepository;
 
@Service
public class AdminService {
 
    @Autowired
    private UserRepository userRepo;
 
    @Autowired
    private AppointmentRepository appointmentRepo;
 
    @Autowired
    private PaymentRepository paymentRepo;
 
    public long getTotalUsers() {
        return userRepo.count();
    }
 
    public long getTotalDoctors() {
        return userRepo.countByRole(Role.DOCTOR);
    }
 
    public long getTotalAppointments() {
        return appointmentRepo.count();
    }
 
    public BigDecimal getTotalRevenue() {
        BigDecimal revenue = paymentRepo.sumTotalPayments();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }
}