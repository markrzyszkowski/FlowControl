package com.krzyszkowski.sandbox.flowcontrol.module.notifications.controllers;

import com.krzyszkowski.sandbox.flowcontrol.module.notifications.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notifications")
public class NotificationsController {

    private final NotificationService notificationService;

    public NotificationsController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @DeleteMapping("dismiss/{id}")
    public ResponseEntity<String> dismiss(@PathVariable long id) {
        notificationService.dismissNotification(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
