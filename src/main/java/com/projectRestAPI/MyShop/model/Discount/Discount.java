package com.projectRestAPI.MyShop.model.Discount;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projectRestAPI.MyShop.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Entity
@Table(name = "Discount")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Discount extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String discountCode;
    private String discountName;
//    Loại giảm giá (VD: 0 - phần trăm, 1 - tiền mặt,...)
    private Integer type;
//    Danh mục áp dụng giảm giá (VD: 1 - sản phẩm, 2 - vận chuyển,...)
    private Integer category;
    private BigDecimal value;
    private BigDecimal maxValue;
    private BigDecimal discountCondition;
    private Integer quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    private Integer status;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DiscountUser> discountUsers;


}
