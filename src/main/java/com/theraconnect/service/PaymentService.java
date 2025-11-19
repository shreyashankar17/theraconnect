package com.theraconnect.service;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
 
import com.theraconnect.model.Payment;
 
public interface PaymentService {
	Payment processPayment(Payment payment);
    Payment getByAppointment(Long appointmentId);
    BigDecimal getTodayRevenue();
    BigDecimal getWeekRevenue();
    BigDecimal getMonthRevenue();
    BigDecimal getRevenueForDate(LocalDate date);
 
    //Map<String, Double> getPaymentRatio();
    Map<String, Long> getPaymentStatusCounts();
    List<Map<String, Object>> searchPayments(
    	    String nameSearch,
    	    Long paymentIdSearch,
    	    String statusFilter,
    	    LocalDate dateSearch
    	);
    Map<String, BigDecimal> getRevenueStats(LocalDate dateSearch);
    Map<String, Long> getRevenueLast7DaysChartData();
 
}