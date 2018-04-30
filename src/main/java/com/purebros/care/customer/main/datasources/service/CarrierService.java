package com.purebros.care.customer.main.datasources.service;

import com.purebros.care.customer.main.datasources.wind.dto.SubscriptionsDto;

import java.util.List;

public interface CarrierService {

    List<SubscriptionsDto> getAllSubscriptions(String carrier, String msisdn);

}
