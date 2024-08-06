package com.projectRestAPI.studensystem.dto.response;

import lombok.*;

import java.util.List;

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
