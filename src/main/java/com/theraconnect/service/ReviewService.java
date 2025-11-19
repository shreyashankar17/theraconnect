package com.theraconnect.service;

import com.theraconnect.model.Review;

public interface ReviewService {
	Review addReview(Review review);
    Review getByAppointment(Long appointmentId);

}
