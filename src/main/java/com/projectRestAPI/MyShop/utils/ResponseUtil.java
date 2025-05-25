package com.projectRestAPI.MyShop.utils;

import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<ResponseObject> buildResponse(
            String status, String message, int code, T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseObject(status, message, code, data), httpStatus);
    }
}
