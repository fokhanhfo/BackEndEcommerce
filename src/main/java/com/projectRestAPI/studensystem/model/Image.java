package com.projectRestAPI.studensystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Table(name = "ImageProduct")
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Image extends BaseEntity {
    @Column(name="name_Image")
    private String name_Image;

    @Column(name = "main_photo")
    private int main_photo;

}
