package com.subscription.billing.scheduler;

import com.subscription.billing.entity.Subscription;
import com.subscription.billing.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SubscriptionExpiryJob {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // Runs every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void expireSubscriptions() {

        LocalDate today = LocalDate.now();

        List<Subscription> activeSubs =
                subscriptionRepository.findByStatus("ACTIVE");

        for (Subscription sub : activeSubs) {
            if (sub.getEndDate().isBefore(today)) {
                sub.setStatus("EXPIRED");
                subscriptionRepository.save(sub);
            }
        }

        System.out.println("Subscription expiry check completed");
    }
}
