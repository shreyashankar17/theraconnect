package com.theraconnect.service.impl;

import com.theraconnect.model.Review;
import com.theraconnect.repository.ReviewRepository;
import com.theraconnect.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getByAppointment(Long appointmentId) {
        return reviewRepository.findByAppointment_AppointmentId(appointmentId);
    }
}