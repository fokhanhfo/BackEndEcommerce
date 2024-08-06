package com.projectRestAPI.studensystem.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(400,"User khong ton tai",HttpStatus.NOT_FOUND),
    INVALID_KEY(400,"Invalid messager key",HttpStatus.BAD_REQUEST),
    VALID_PASSWORD(400,"Password phai lon hon 2 ki tu",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(401,"You need to login",HttpStatus.UNAUTHORIZED),
    METHOD_NOT_SUPPORTED(405, "HTTP Method not supported", HttpStatus.METHOD_NOT_ALLOWED),
    UNAUTHORIZED(403,"You do not have permission",HttpStatus.FORBIDDEN),
    READ_WRITE_ERROR(500, "Error during read/write operation", HttpStatus.INTERNAL_SERVER_ERROR),

    //cart
    CART_NOT_FOUND(404, "Cart not found", HttpStatus.NOT_FOUND),

    //category
    CATEGORY_NOT_FOUND(404, "Category not found", HttpStatus.NOT_FOUND),

    //product
    PRODUCT_ALREADY_EXISTS(409, "Product already exists", HttpStatus.CONFLICT),
    PRODUCT_NOT_FOUND(404, "Product not found", HttpStatus.NOT_FOUND),

    //image
    IMAGE_NOT_FOUND(404, "Image not found", HttpStatus.NOT_FOUND),

    //bill
    BILL_NOT_FOUND(404, "Bill not found", HttpStatus.NOT_FOUND),

    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
