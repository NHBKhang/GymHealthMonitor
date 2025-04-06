package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Subscription;
import java.util.List;
import java.util.Map;

public interface SubscriptionRepository {

    List<Subscription> getSubscriptions(Map<String, String> params);

    Subscription getSubscriptionById(int id);

    Subscription createOrUpdateSubscription(Subscription subscription);

    long countSubscriptions(Map<String, String> params);
    
    String generateNextCode();
    
}
