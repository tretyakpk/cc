package com.purebros.care.customer.main.service;

import com.purebros.care.customer.main.dto.CustomUserDetails;
import com.purebros.care.customer.main.dto.SubscriptionsDto;
import com.purebros.care.customer.main.dto.SubscriptionsInfDto;

public interface CarrierService {

    SubscriptionsDto[] getAllSubscriptions(String carrier, String msisdn, CustomUserDetails userDetails);

    SubscriptionsInfDto[] getAllSubscriptionsInfo(String carrier, String msisdn, CustomUserDetails userDetails);

}
