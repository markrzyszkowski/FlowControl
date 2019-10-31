package com.krzyszkowski.sandbox.flowcontrol.module.notifications.config;

import com.krzyszkowski.sandbox.flowcontrol.module.notifications.services.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationsConfiguration {

    private final NotificationService notificationService;

    public NotificationsConfiguration(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Bean
    public NotificationService notificationService() {
        return notificationService;
    }
}
