package com.healthmonitor.services;

import com.healthmonitor.pojo.Notification;
import java.util.List;
import java.util.Map;

public interface NotificationService {

    List<Notification> getNotifications(Map<String, String> params);

    Notification getNotificationById(int id);

    Notification createOrUpdateNotification(Notification notification);

    long countNotifications(Map<String, String> params);
    
}
