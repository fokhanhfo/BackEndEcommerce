package com.projectRestAPI.MyShop.dto.request.Discount;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DiscountRequest {
    private Long id;

    @NotBlank
    private String discountCode;

    @NotBlank
    private String discountName;

    @NotNull
    private Integer type;

    @NotNull
    private Integer category;

    @NotNull
    private BigDecimal value;

    private BigDecimal maxValue;

    private BigDecimal discountCondition;

    @NotNull
    private Integer quantity;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    @NotNull
    private Integer status;
}
