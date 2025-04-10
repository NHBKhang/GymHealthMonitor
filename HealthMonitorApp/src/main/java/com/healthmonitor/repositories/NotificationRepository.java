package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Notification;
import java.util.List;
import java.util.Map;

public interface NotificationRepository {

    static final int PAGE_SIZE = 20;

    List<Notification> getNotifications(Map<String, String> params);

    Notification getNotificationById(int id);

    Notification createOrUpdateNotification(Notification notification);

    long countNotifications(Map<String, String> params);
    
    public static int getPageSize() {
        return NotificationRepository.PAGE_SIZE;
    }
}
