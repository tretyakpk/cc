package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.datasources.service.CarrierServiceImpl;
import com.purebros.care.customer.main.datasources.wind.dto.SubscriptionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Component
@RestController()
@RequestMapping(value = "/subscriptions")
public class SubscriptionsController {

    private final CarrierServiceImpl carrierService;

    @Autowired
    public SubscriptionsController(CarrierServiceImpl carrierService) {
        this.carrierService = carrierService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public List<SubscriptionsDto> findAllSubscriptions(@RequestParam("carrier") String carrier, @RequestParam("msisdn") String msisdn){
        return carrierService.getAllSubscriptions(carrier, msisdn);
    }
}
