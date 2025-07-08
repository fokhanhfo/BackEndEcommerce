package com.projectRestAPI.MyShop.repository;


import com.projectRestAPI.MyShop.model.Notification.Notification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends BaseRepository<Notification,Long>{
    List<Notification> findByRecipient(String recipient);
    List<Notification> findByRecipientAndIsReadFalse(String recipient);
}
