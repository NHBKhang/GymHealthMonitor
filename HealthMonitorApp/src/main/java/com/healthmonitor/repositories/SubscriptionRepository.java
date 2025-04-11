package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Subscription;
import java.util.List;
import java.util.Map;

public interface SubscriptionRepository {

    static final int PAGE_SIZE = 10;

    List<Subscription> getSubscriptions(Map<String, String> params);

    Subscription getSubscriptionById(int id);

    Subscription createOrUpdateSubscription(Subscription subscription);

    Subscription createByPackageIdAndUsername(int packageId, String username);

    long countSubscriptions(Map<String, String> params);
    
    List<Subscription> getSubscriptionsByUsername(String username);
    
    String generateNextCode();

    public static int getPageSize() {
        return SubscriptionRepository.PAGE_SIZE;
    }
    
}
