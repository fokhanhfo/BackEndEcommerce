package com.projectRestAPI.studensystem.dto.request;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ImageRequest {
    private String name_Image;
    private int main_photo;
}
