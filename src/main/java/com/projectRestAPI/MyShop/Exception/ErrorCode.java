package com.projectRestAPI.MyShop.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(400, "Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST),
    //user
    USER_NOT_FOUND(400,"User khong ton tai",HttpStatus.NOT_FOUND),
    OLD_PASSWORD_INCORRECT(400,"Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST),
    PASSWORD_CONFIRM_MISMATCH(400,"Mật khẩu mới và xác nhận mật khẩu không khớp", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(409, "Email bị trùng", HttpStatus.CONFLICT),

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
    PRODUCT_ALREADY_EXISTS(409, "Tên sản phẩm bị trùng", HttpStatus.CONFLICT),
    PRODUCT_NOT_FOUND(404, "Product not found", HttpStatus.NOT_FOUND),

    //Product_Detail
    PRODUCT_DETAIL_NOT_FOUND(404, "Product detail not found", HttpStatus.NOT_FOUND),

    //image
    IMAGE_NOT_FOUND(404, "Image not found", HttpStatus.NOT_FOUND),

    //bill
    BILL_NOT_FOUND(404, "Bill not found", HttpStatus.NOT_FOUND),
    INSUFFICIENT_QUANTITY(400,"Rất tiếc, số lượng bạn đặt hiện không đủ trong kho",HttpStatus.BAD_REQUEST),

    //size
    SIZE_NOT_FOUND(404, "Size not found", HttpStatus.NOT_FOUND),
    SIZE_ALREADY_EXISTS(409, "Số size đã tồn tại", HttpStatus.CONFLICT),

    //discountUser
    DISCOUNT_NOT_FOUND(404, "Mã giảm giá không tồn tại", HttpStatus.NOT_FOUND),
    DISCOUNT_INACTIVE(400, "Giảm giá không hoạt động", HttpStatus.BAD_REQUEST),

    //discount
    DISCOUNT_USER_NOT_FOUND(404, "Discount user not found", HttpStatus.NOT_FOUND),
    DUPLICATE_VALUE(400, "Giá trị đã tồn tại",HttpStatus.BAD_REQUEST),
    DISCOUNT_EXPIRED(400, "Mã giảm giá đã hết hạn", HttpStatus.BAD_REQUEST),
    DISCOUNT_NOT_ENOUGH_QUANTITY(400, "Số lượng mã giảm giá không đủ", HttpStatus.BAD_REQUEST),


    //discountPeriod
    DISCOUNT_PERIOD_NOT_FOUND(404, "Discount Period not found", HttpStatus.NOT_FOUND),
    DUPLICATE_DISCOUNT_CODE(400, "Mã giảm giá đã tồn tại", HttpStatus.BAD_REQUEST),
    DISCOUNT_PERIOD_INACTIVE(400, "Mã giảm giá không hoạt động", HttpStatus.BAD_REQUEST),

    //color
    COLOR_ALREADY_EXISTS(409, "Tên màu đã tồn tại", HttpStatus.CONFLICT),
    KEY_COLOR_ALREADY_EXISTS(409, "key màu đã tồn tại", HttpStatus.CONFLICT),
    DUPLICATE_NAME(409,"Tên màu đã tồn tại",HttpStatus.CONFLICT),
    DUPLICATE_KEY(409,"key màu đã tồn tại",HttpStatus.CONFLICT),


    //otp
    OTP_NOT_FOUND(404, "Otp not found", HttpStatus.NOT_FOUND),
    OTP_IS_INCORRECT(400,"OTP không đúng.",HttpStatus.BAD_REQUEST),
    OTP_HAS_EXPIRED(400,"OTP đã hết hạn.",HttpStatus.BAD_REQUEST),
    OTP_PURPOSE_INVALID(400, "Mục đích xác thực OTP không hợp lệ.", HttpStatus.BAD_REQUEST),

    //ShippingAddress
    ShippingAddress_NOT_FOUND(404, "Địa chỉ giao hàng không tồn tại", HttpStatus.NOT_FOUND),
    MAX_SHIPPING_ADDRESS_REACHED(400, "Chỉ được có 4 địa chỉ giao hàng", HttpStatus.BAD_REQUEST),

    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
