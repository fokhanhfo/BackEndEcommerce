package com.projectRestAPI.MyShop.dto.request;

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
    private ColorRequest color;
}
