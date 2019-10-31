package com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.mapping;

import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.Notification;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto.NotificationDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto.NotificationDto;

public interface NotificationMapper {

    Notification fromDto(NotificationDto notificationDto);

    NotificationDisplayDto toDisplayDto(Notification notification);
}
