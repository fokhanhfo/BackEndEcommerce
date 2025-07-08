package com.projectRestAPI.MyShop.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ShippingAddressResponse {
    private Long id;
    private String recipientName;
    private String phone;
    private String idProvince;
    private String idDistrict;
    private String idCommune;
    private String addressDetail;
    private Boolean isDefault;
    private Long userId;
}
