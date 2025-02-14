package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ImageResponseAll {
    private String name;
    private String type;
    private byte[] data;
}
