package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.model.Discount.DiscountUser;

public interface EmailService{
    void sendOtpEmail(String to, String otp,String text);
}
