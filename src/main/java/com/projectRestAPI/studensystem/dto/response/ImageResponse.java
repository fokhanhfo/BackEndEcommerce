package com.projectRestAPI.studensystem.dto.response;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ImageResponse {
    private Long id;
    private String urlFile;
}
