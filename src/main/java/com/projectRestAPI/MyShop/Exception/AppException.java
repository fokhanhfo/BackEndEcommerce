package com.projectRestAPI.MyShop.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppException extends  RuntimeException {
    private ErrorCode errorCode;
}
