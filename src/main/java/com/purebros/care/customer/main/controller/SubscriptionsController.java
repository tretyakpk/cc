package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.dto.CustomUserDetails;
import com.purebros.care.customer.main.dto.SubscriptionsDto;
import com.purebros.care.customer.main.dto.SubscriptionsInfDto;
import com.purebros.care.customer.main.service.CarrierServiceImpl;
import com.purebros.care.customer.main.service.UnsubscriptionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Arrays;


@RestController()
@RequestMapping(value = "/subscriptions")
public class SubscriptionsController {

    private final static Logger logger = LoggerFactory.getLogger(SubscriptionsController.class);

    private final CarrierServiceImpl carrierService;

    private final UnsubscriptionServiceImpl unsubscriptionService;


    @Autowired
    public SubscriptionsController(CarrierServiceImpl carrierService, UnsubscriptionServiceImpl unsubscriptionService) {
        this.carrierService = carrierService;
        this.unsubscriptionService = unsubscriptionService;
    }

    @PreAuthorize("hasAnyRole('ROLE_GET_USER_SERVICE_SUBSCRIPTIONS'," +
            "'ROLE_SERVICE_UNSUBSCRIPTION')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SubscriptionsDto[] all(@NotNull @RequestParam("carrier") String carrier,
                                  @NotNull @RequestParam("msisdn") String msisdn,
                                  Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        SubscriptionsDto[]  allSubs = carrierService.getAllSubscriptions(msisdn, carrier, userDetails);
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SERVICE_UNSUBSCRIPTION")))
            Arrays.stream(allSubs).forEach(sub -> {
                sub.setUnsubscriptionUrl(null);
            });
        return allSubs;
    }

    @PreAuthorize("hasRole('ROLE_SERVICE_UNSUBSCRIPTION')")
    @RequestMapping(value = "/deactivate", method = RequestMethod.POST)
    public String info(@NotNull @RequestParam("link") String link,
                       Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String result = unsubscriptionService.deactivate(link);
        logger.info(userDetails.getUsername() + ": deactivation action: result: " + result + " link: " + link);
        return result;
    }

    @PreAuthorize("hasAnyRole('ROLE_GET_USER_BILLINGS', " +
            "'VODAFONE_REFUND')")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public SubscriptionsInfDto[] deactivate(@NotNull @RequestParam("carrier") String carrier,
                                            @NotNull @RequestParam("msisdn") String msisdn,
                                            Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        SubscriptionsInfDto[] allInfo = carrierService.getAllSubscriptionsInfo(msisdn, carrier, userDetails);
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("VODAFONE_REFUND")))
            Arrays.stream(allInfo).forEach(sub -> {
                sub.setRefundUrl(null);
            });
        return allInfo;
    }
}
