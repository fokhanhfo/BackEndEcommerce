package com.projectRestAPI.MyShop.dto.request.Discount;

import com.projectRestAPI.MyShop.dto.request.UserRequest;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DiscountUserAllRequest {
    private Long id;
    private boolean isUsed;
    private Integer status;
    private List<UserRequest> users;
    private  DiscountRequest discount;
}
