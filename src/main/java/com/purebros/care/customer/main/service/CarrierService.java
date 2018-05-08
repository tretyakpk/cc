package com.purebros.care.customer.main.service;

import com.purebros.care.customer.main.dto.CSP;
import com.purebros.care.customer.main.dto.SubscriptionsDto;
import com.purebros.care.customer.main.dto.SubscriptionsInfDto;

import java.util.ArrayList;

public interface CarrierService {

    SubscriptionsDto[] getAllSubscriptions(String carrier, String msisdn, ArrayList<CSP> csps);

    SubscriptionsInfDto[] getAllSubscriptionsInfo(String carrier, String msisdn, ArrayList<CSP> csps);

}
