package com.theraconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.theraconnect.model.Review;
import com.theraconnect.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Review add(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @GetMapping("/{appointmentId}")
    public Review get(@PathVariable Long appointmentId) {
        return reviewService.getByAppointment(appointmentId);
    }
}