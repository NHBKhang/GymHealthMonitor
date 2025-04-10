package com.healthmonitor.services.impl;

import com.healthmonitor.pojo.Notification;
import com.healthmonitor.pojo.Notification.NotificationStatus;
import com.healthmonitor.repositories.NotificationRepository;
import com.healthmonitor.services.NotificationService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    
    @Autowired
    private NotificationRepository packageRepository;
    
    @Override
    public List<Notification> getNotifications(Map<String, String> params) {
        return this.packageRepository.getNotifications(params);
    }
    
    @Override
    public Notification createOrUpdateNotification(Notification pkg) {
        if (pkg.getStatus() == null || pkg.getStatusName().isEmpty()) {
            pkg.setStatus(NotificationStatus.UNREAD);
        }
        
        return this.packageRepository.createOrUpdateNotification(pkg);
    }
    
    @Override
    public long countNotifications(Map<String, String> params) {
        return this.packageRepository.countNotifications(params);
    }

    @Override
    public Notification getNotificationById(int id) {
        return this.packageRepository.getNotificationById(id);
    }
    
}
