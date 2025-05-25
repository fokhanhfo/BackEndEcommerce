package com.projectRestAPI.MyShop.model.Discount;


import com.projectRestAPI.MyShop.model.BaseEntity;
import com.projectRestAPI.MyShop.model.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
@SuperBuilder
@Entity
@Table(name = "DiscountUser")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiscountUser extends BaseEntity {
    private boolean isUsed;

    private Integer status;

    private Long BillId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

}
