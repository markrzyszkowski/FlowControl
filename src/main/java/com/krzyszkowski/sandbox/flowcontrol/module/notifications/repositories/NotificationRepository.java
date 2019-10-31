package com.krzyszkowski.sandbox.flowcontrol.module.notifications.repositories;

import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUserId(Long userId, Pageable pageable);

    Long countByUserId(Long userId);
}
