package com.projectRestAPI.MyShop.dto.response.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.UserResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DiscountUserResponse {
    private Long id;
    private boolean isUsed;
    private Integer status;
    private UserResponse users;
    private DiscountResponse discount;
}
