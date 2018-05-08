package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.dto.CustomUserDetails;
import com.purebros.care.customer.main.dto.SubscriptionsDto;
import com.purebros.care.customer.main.dto.SubscriptionsInfDto;
import com.purebros.care.customer.main.service.CarrierServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


@RestController()
@RequestMapping(value = "/subscriptions")
public class SubscriptionsController {

    private final static Logger logger = LoggerFactory.getLogger(SubscriptionsController.class);

    private final CarrierServiceImpl carrierService;

    @Autowired
    public SubscriptionsController(CarrierServiceImpl carrierService) {
        this.carrierService = carrierService;
    }

    @PreAuthorize("hasAnyRole('ROLE_GET_USER_SERVICE_SUBSCRIPTIONS'," +
            "'ROLE_GET_USER_BILLINGS', " +
            "'ROLE_SERVICE_UNSUBSCRIPTION', " +
            "'ROLE_VODAFONE_REFUND')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SubscriptionsDto[] all(@NotNull @RequestParam("carrier") String carrier,
                                  @NotNull @RequestParam("msisdn") String msisdn,
                                  Authentication authentication){

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return carrierService.getAllSubscriptions(msisdn, carrier, userDetails);

    }

    @PreAuthorize("hasAnyRole('ROLE_GET_USER_SERVICE_SUBSCRIPTIONS'," +
            "'ROLE_GET_USER_BILLINGS', " +
            "'ROLE_SERVICE_UNSUBSCRIPTION', " +
            "'ROLE_VODAFONE_REFUND')")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public SubscriptionsInfDto[] info(@NotNull @RequestParam("carrier") String carrier,
                                  @NotNull @RequestParam("msisdn") String msisdn,
                                  Authentication authentication){

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return carrierService.getAllSubscriptionsInfo(msisdn, carrier, userDetails
        );
    }
}
