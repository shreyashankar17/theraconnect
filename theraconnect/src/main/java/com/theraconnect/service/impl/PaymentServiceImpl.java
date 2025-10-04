package com.theraconnect.service.impl;
 
import com.theraconnect.enums.PaymentStatus;
import com.theraconnect.model.Payment;
import com.theraconnect.repository.PaymentRepository;
import com.theraconnect.service.PaymentService;
 
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
 
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class PaymentServiceImpl implements PaymentService {
 
    @Autowired
    private PaymentRepository paymentRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
 
    @Override
    public Payment processPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
 
    @Override
    public Payment getByAppointment(Long appointmentId) {
        return paymentRepository.findByAppointment_AppointmentId(appointmentId);
    }
    
    
    @Override
    public BigDecimal getTodayRevenue() {
        return paymentRepository.getTodayRevenue();
    }
 
    @Override
    public BigDecimal getWeekRevenue() {
        return paymentRepository.getWeekRevenue();
    }
 
    @Override
    public BigDecimal getMonthRevenue() {
        return paymentRepository.getMonthRevenue();
    }
 
    @Override
    public BigDecimal getRevenueForDate(LocalDate date) {
        return paymentRepository.getRevenueForDate(date);
    }
 
    @Override
    public List<Map<String, Object>> searchPayments(
            String nameSearch,
            Long paymentIdSearch,
            String statusFilter,
            LocalDate dateSearch) {
    	
    	if (nameSearch != null) {
            nameSearch = nameSearch.trim();
            if (nameSearch.isEmpty()) nameSearch = null;
        }
 
        // Sanitize status filter
        if (statusFilter != null) {
            statusFilter = statusFilter.trim();
            if (statusFilter.isEmpty()) statusFilter = null;
        }
 
        // Ignore paymentId if zero or negative
        if (paymentIdSearch != null && paymentIdSearch <= 0) {
            paymentIdSearch = null;
        }
    	
        return paymentRepository.findFilteredFlatPayments(
            nameSearch,
            paymentIdSearch,
            statusFilter,
            dateSearch
        );
    }
 
    public Map<String, Long> getPaymentStatusCounts() {
        List<Object[]> resultList = entityManager.createQuery(
            "SELECT p.paymentStatus, COUNT(p) FROM Payment p GROUP BY p.paymentStatus"
        ).getResultList();
 
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : resultList) {
        	PaymentStatus statusEnum = (PaymentStatus) row[0];
        	String status = statusEnum.name(); // now it's a String: "PAID", "FAILED", etc.
        	Long count = (Long) row[1];
            map.put(status, count);
        }
        return map;
    }
    
    @Override
    public Map<String, BigDecimal> getRevenueStats(LocalDate dateSearch) {
        Map<String, BigDecimal> stats = new HashMap<>();
 
        stats.put("today", getTodayRevenue());
        stats.put("week", getWeekRevenue());
        stats.put("month", getMonthRevenue());
        stats.put("custom", getRevenueForDate(dateSearch));
 
        return stats;
    }
    
    public Map<String, Long> getRevenueLast7DaysChartData() {
    	
    	List<Object[]> rawData = paymentRepository.getDailyRevenueLast7Days();
 
        // Step 1: Convert DB result to Map<LocalDate, Long>
        Map<LocalDate, Long> revenueMap = new HashMap<>();
        for (Object[] row : rawData) {
            LocalDate date = ((Date) row[0]).toLocalDate();
            Long total = ((BigDecimal) row[1]).longValue();
            revenueMap.put(date, total);
        }
 
        // Step 2: Fill missing dates with 0
        Map<String, Long> finalMap = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String label = date.format(DateTimeFormatter.ofPattern("dd MMM"));
            finalMap.put(label, revenueMap.getOrDefault(date, 0L));
        }
 
        return finalMap;
    	
    }
 
 
    
}