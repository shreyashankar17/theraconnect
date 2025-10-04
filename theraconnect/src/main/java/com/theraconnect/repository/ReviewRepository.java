package com.theraconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theraconnect.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	Review findByAppointment_AppointmentId(Long appointmentId);
}
