package com.projectRestAPI.MyShop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginType {
    NORMAL_LOGIN(0),
    FACEBOOK_LOGIN(1),
    GOOGLE_LOGIN(2),
    GITHUB_LOGIN(3);

    private final Integer loginType;
}
