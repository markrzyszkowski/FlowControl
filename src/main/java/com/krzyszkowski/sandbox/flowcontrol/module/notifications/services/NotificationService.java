package com.krzyszkowski.sandbox.flowcontrol.module.notifications.services;

import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto.NotificationDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto.NotificationDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationService {

    boolean hasNotifications(String username);

    Page<NotificationDisplayDto> getUserNotifications(String username);

    void addNotifications(List<NotificationDto> notificationDto);

    void dismissNotification(Long id);
}
