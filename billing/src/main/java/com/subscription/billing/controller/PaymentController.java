package com.subscription.billing.controller;

import com.subscription.billing.entity.Payment;
import com.subscription.billing.entity.User;
import com.subscription.billing.repository.PaymentRepository;
import com.subscription.billing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(@RequestBody Payment payment) {

        Long userId = payment.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        payment.setUser(user);

        Payment saved = paymentRepository.save(payment);

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getPaymentsByUser(@PathVariable Long id) {
        return ResponseEntity.ok(paymentRepository.findByUser_Id(id));
    }
}
