package com.projectRestAPI.studensystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@NoArgsConstructor
@Table(name = "ImageProduct")
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Image extends BaseEntity {
    private String name;
    private String type;
    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] file;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
