package com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.mapping;

import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.Notification;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto.NotificationDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto.NotificationDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public Notification fromDto(NotificationDto notificationDto) {
        var notification = new Notification();
        notification.setUser(notificationDto.getUser());
        notification.setText(notificationDto.getText());
        return notification;
    }

    @Override
    public NotificationDisplayDto toDisplayDto(Notification notification) {
        return new NotificationDisplayDto(notification.getId(), notification.getText());
    }
}
