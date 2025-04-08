package com.healthmonitor.services.impl;

import com.healthmonitor.pojo.Subscription;
import com.healthmonitor.pojo.Subscription.SubscriptionStatus;
import com.healthmonitor.repositories.SubscriptionRepository;
import com.healthmonitor.services.SubscriptionService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> getSubscriptions(Map<String, String> params) {
        return this.subscriptionRepository.getSubscriptions(params);
    }

    @Override
    public Subscription createOrUpdateSubscription(Subscription subscription) {
        if (subscription.getStatus() == null || subscription.getStatus().getDescription().isEmpty()) {
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }

        if (subscription.getCode() == null || subscription.getCode().isEmpty()) {
            subscription.setCode(this.generateNextCode());
        }
        
        return this.subscriptionRepository.createOrUpdateSubscription(subscription);
    }

    @Override
    public long countSubscriptions(Map<String, String> params) {
        return this.subscriptionRepository.countSubscriptions(params);
    }

    @Override
    public Subscription getSubscriptionById(int id) {
        return this.subscriptionRepository.getSubscriptionById(id);
    }

    @Override
    public String generateNextCode() {
        return subscriptionRepository.generateNextCode();
    }

}
