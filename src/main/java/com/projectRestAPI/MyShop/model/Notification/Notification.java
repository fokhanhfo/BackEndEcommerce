package com.projectRestAPI.MyShop.model.Notification;

import com.projectRestAPI.MyShop.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name = "Notification")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Notification extends BaseEntity {
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isRead = false;
    private String recipient = "admin";
}

