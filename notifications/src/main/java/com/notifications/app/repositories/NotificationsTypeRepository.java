package com.notifications.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notifications.app.models.NotificationsType;



@Repository
public interface NotificationsTypeRepository extends JpaRepository<NotificationsType, Integer>  {

	NotificationsType findOneByType(String type);
}
