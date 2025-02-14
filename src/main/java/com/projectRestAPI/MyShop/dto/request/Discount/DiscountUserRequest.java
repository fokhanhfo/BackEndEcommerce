package com.projectRestAPI.MyShop.dto.request.Discount;

import com.projectRestAPI.MyShop.dto.request.UserRequest;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DiscountUserRequest {
    private Long id;
    private boolean isUsed;
    private Integer status;
    private UserRequest users;
    private  DiscountRequest discount;
}
