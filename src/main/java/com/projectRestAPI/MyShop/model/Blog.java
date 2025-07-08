package com.projectRestAPI.MyShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Entity
@Table(name = "Blog")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Blog extends BaseEntity{
    private String image;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;


    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean approved;
}
