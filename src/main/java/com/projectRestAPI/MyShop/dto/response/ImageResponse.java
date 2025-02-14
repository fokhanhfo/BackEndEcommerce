package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ImageResponse {
    private Long id;
    private String urlFile;
    private ColorResponse colorResponse;
}
