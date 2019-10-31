package com.krzyszkowski.sandbox.flowcontrol.module.notifications.services;

import com.krzyszkowski.sandbox.flowcontrol.core.repositories.UserRepository;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto.NotificationDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto.NotificationDto;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.mapping.NotificationMapper;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.repositories.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserRepository userRepository,
                                   NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    @Transactional
    public boolean hasNotifications(String username) {
        var self = userRepository.findByUsername(username)
                                 .orElseThrow()
                                 .getId();
        return notificationRepository.countByUserId(self) > 0;
    }

    @Override
    @Transactional
    public Page<NotificationDisplayDto> getUserNotifications(String username) {
        var self = userRepository.findByUsername(username)
                                 .orElseThrow()
                                 .getId();
        return notificationRepository.findByUserId(self, PageRequest.of(0, 20))
                                     .map(notificationMapper::toDisplayDto);
    }

    @Override
    @Transactional
    public void addNotifications(List<NotificationDto> notificationDtos) {
        notificationRepository.saveAll(notificationDtos.stream()
                                                       .map(notificationMapper::fromDto)
                                                       .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public void dismissNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
