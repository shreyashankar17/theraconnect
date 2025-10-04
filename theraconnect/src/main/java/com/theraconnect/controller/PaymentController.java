package com.theraconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.theraconnect.model.Payment;
import com.theraconnect.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment pay(@RequestBody Payment payment) {
        return paymentService.processPayment(payment);
    }

    @GetMapping("/{appointmentId}")
    public Payment get(@PathVariable Long appointmentId) {
        return paymentService.getByAppointment(appointmentId);
    }
}