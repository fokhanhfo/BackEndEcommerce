package com.projectRestAPI.MyShop.model.Discount;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projectRestAPI.MyShop.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SuperBuilder
@Entity
@Table(name = "Discount")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Discount extends BaseEntity {
    private String discountCode;
    private String discountName;
    private Integer type;
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

}
