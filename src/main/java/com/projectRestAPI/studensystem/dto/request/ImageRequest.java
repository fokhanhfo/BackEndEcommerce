package com.projectRestAPI.studensystem.dto.request;

import com.projectRestAPI.studensystem.model.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ImageRequest {
    private List<MultipartFile> file;
}
