package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
