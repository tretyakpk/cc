package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.datasources.user.dto.CustomUserDetails;
import com.purebros.care.customer.main.datasources.wind.dto.SubscriptionsDto;
import com.purebros.care.customer.main.service.CarrierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public List<SubscriptionsDto> findAllSubscriptions(@NotNull @RequestParam("carrier") String carrier,
                                                       @NotNull @RequestParam("msisdn") String msisdn){
        return carrierService.getAllSubscriptions(carrier, msisdn);
    }

    @RequestMapping(value = "/secured", method = RequestMethod.GET)
    public List<SubscriptionsDto> findAllSubscriptionsSecured(Authentication authentication){

        // Get current authenticated user
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getCsps());

        return carrierService.getAllSubscriptions("wind", "1234");
    }
}