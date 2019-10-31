package com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto;

import com.krzyszkowski.sandbox.flowcontrol.core.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private User user;

    private String text;
}
