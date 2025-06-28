package com.projectRestAPI.MyShop.dto.StatisticsInterface;

public interface UserStatisticsByMonth {
    String getMonth();
    Long getIcount();
    Long getMaleCount();
    Long getFemaleCount();
    Long getOtherCount();
}
