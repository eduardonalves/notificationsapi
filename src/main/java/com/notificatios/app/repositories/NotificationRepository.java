package com.notificatios.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notificatios.app.models.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
