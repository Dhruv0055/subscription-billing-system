package com.subscription.billing.repository;

import com.subscription.billing.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


    List<Subscription> findByStatus(String status);
    Subscription findTopByUserIdOrderByEndDateDesc(Long userId);
    List<Subscription> findByUserIdAndStatus(Long userId, String status);
}
