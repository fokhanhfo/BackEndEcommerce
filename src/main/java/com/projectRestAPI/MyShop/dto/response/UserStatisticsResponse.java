package com.projectRestAPI.MyShop.dto.response;

import com.projectRestAPI.MyShop.dto.StatisticsInterface.GenderCount;
import com.projectRestAPI.MyShop.dto.StatisticsInterface.RoleCount;
import com.projectRestAPI.MyShop.dto.StatisticsInterface.UserMonthlyCount;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserStatisticsResponse {
    private List<UserMonthlyCount> totalPerMonth;
    private GenderCount genderCount;
    private List<RoleCount> roleCounts;
}
