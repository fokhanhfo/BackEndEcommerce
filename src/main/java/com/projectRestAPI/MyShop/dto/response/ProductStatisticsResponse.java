package com.projectRestAPI.MyShop.dto.response;

import com.projectRestAPI.MyShop.dto.ProductStatistics.*;
import com.projectRestAPI.MyShop.model.SanPham.Product;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductStatisticsResponse {
    private List<ProductStatusDTO> productStatusStats;
    private ProductQuantityView topProduct;
    private List<ProductQuantityDTO> productQuantityDTOS;
    private ProductSalesView productSalesView;
}
