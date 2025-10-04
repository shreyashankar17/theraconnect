package com.theraconnect.repository;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
import com.theraconnect.model.Payment;
 
public interface PaymentRepository extends JpaRepository<Payment,Long>{
 
	 Payment findByAppointment_AppointmentId(Long appointmentId);
 
	 @Query("SELECT SUM(p.amount) FROM Payment p")
	BigDecimal sumTotalPayments();
	 @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE DATE(p.timestamp) = CURRENT_DATE")
	 BigDecimal getTodayRevenue();
 
	 @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE WEEK(p.timestamp) = WEEK(CURRENT_DATE)")
	 BigDecimal getWeekRevenue();
 
	 @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE MONTH(p.timestamp) = MONTH(CURRENT_DATE)")
	 BigDecimal getMonthRevenue();
 
	 @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE DATE(p.timestamp) = :customDate")
	 BigDecimal getRevenueForDate(LocalDate customDate);
 
	 @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentStatus = 'PAID'")
	 Long countPaidPayments();
 
	 @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentStatus = 'PENDING'")
	 Long countPendingPayments();
 
	 @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentStatus = 'FAILED'")
	 Long countFailedPayments();
	 @Query(value = """
			    SELECT 
			        p.payment_id AS payment_id,
			        pat.name AS patient_name,
			        doc.name AS doctor_name,
			        p.amount AS amount,
			        p.payment_status AS payment_status,
			        DATE(p.timestamp) AS date,
			        p.timestamp AS timestamp,
			        a.appointment_id AS appointment_id
			    FROM 
			        payment p
			    JOIN appointment a ON p.appointment_appointment_id = a.appointment_id
			    JOIN users pat ON a.patient_user_id = pat.user_id
			    JOIN users doc ON a.doctor_user_id = doc.user_id
			    WHERE 
			        (:nameSearch IS NULL OR LOWER(pat.name) LIKE LOWER(CONCAT('%', :nameSearch, '%')) OR LOWER(doc.name) LIKE LOWER(CONCAT('%', :nameSearch, '%')))
			        AND (:paymentIdSearch IS NULL OR p.payment_id = :paymentIdSearch)
			        AND (:statusFilter IS NULL OR p.payment_status = :statusFilter)
			        AND (:dateSearch IS NULL OR DATE(p.timestamp) = :dateSearch)
			    ORDER BY p.timestamp DESC
			    """, nativeQuery = true)
			List<Map<String, Object>> findFilteredFlatPayments(
			        @Param("nameSearch") String nameSearch,
			        @Param("paymentIdSearch") Long paymentIdSearch,
			        @Param("statusFilter") String statusFilter,
			        @Param("dateSearch") LocalDate dateSearch
			);
 
	 @Query("SELECT p.paymentStatus, COUNT(p) FROM Payment p GROUP BY p.paymentStatus")
	 List<Object[]> countPaymentsGroupedByStatus();

	 @Query(value = "SELECT DATE(p.timestamp) AS payDate, SUM(p.amount) AS total " +
             "FROM payment p " +
             "WHERE p.timestamp >= CURRENT_DATE - INTERVAL 6 DAY " +
             "GROUP BY payDate ORDER BY payDate", nativeQuery = true)
List<Object[]> getDailyRevenueLast7Days();
 
		
 
}