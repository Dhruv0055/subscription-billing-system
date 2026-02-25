package com.subscription.billing.controller;

import com.subscription.billing.entity.Plan;
import com.subscription.billing.entity.Subscription;
import com.subscription.billing.entity.User;
import com.subscription.billing.repository.PlanRepository;
import com.subscription.billing.repository.SubscriptionRepository;
import com.subscription.billing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanRepository planRepository;

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestParam Long userId,
                                       @RequestParam Long planId) {


        List<Subscription> activeSubs =
                subscriptionRepository.findByUserIdAndStatus(userId, "ACTIVE");

        if (!activeSubs.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("You already have an ACTIVE subscription");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(plan);

        LocalDate startDate = LocalDate.now();
        subscription.setStartDate(startDate);

        LocalDate endDate = startDate.plusDays(plan.getDurationInDays());
        subscription.setEndDate(endDate);

        subscription.setStatus("ACTIVE");

        return ResponseEntity.ok(subscriptionRepository.save(subscription));
    }

    @GetMapping("/my")
    public ResponseEntity<?> mySubscription() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription sub = subscriptionRepository
                .findTopByUserIdOrderByEndDateDesc(user.getId());

        if (sub == null) {
            return ResponseEntity.ok("No subscription found");
        }

        return ResponseEntity.ok(sub);
    }
}
