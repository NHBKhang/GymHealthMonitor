package com.healthmonitor.serializers;

import com.healthmonitor.pojo.Notification;

public class NotificationSerializer extends Serializer<NotificationSerializer> {

    public NotificationSerializer(Notification notification) {
        this.id = notification.getId();
    }
}
