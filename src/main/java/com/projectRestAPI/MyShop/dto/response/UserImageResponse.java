package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserImageResponse {
    private Long id;
    private String userImage;
}
