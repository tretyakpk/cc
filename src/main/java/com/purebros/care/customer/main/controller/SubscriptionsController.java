package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.dto.CustomUserDetails;
import com.purebros.care.customer.main.dto.SubscriptionsDto;
import com.purebros.care.customer.main.service.CarrierServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController()
@RequestMapping(value = "/subscriptions")
public class SubscriptionsController {

    private final static Logger logger = LoggerFactory.getLogger(SubscriptionsController.class);

    private final CarrierServiceImpl carrierService;

    @Autowired
    public SubscriptionsController(CarrierServiceImpl carrierService) {
        this.carrierService = carrierService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List all(@NotNull @RequestParam("carrier") String carrier,
                    @NotNull @RequestParam("msisdn") String msisdn,
                    Authentication authentication){

        if(authentication.isAuthenticated()) {
            System.out.println(authentication.getPrincipal());
            System.out.println(authentication.getDetails());
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        }

        //RestTemplate restTemplate = new RestTemplate();
        //ArrayList<SubscriptionsDto> allSubs = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", ArrayList<SubscriptionsDto>.cl);
        ArrayList<SubscriptionsDto> allSubs = new ArrayList<>();

        logger.info("Count of subscriptions: " + allSubs.size());
        return allSubs;

    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public List details(Authentication authentication){
        if(authentication.isAuthenticated()) {
            System.out.println(authentication.getPrincipal());
            System.out.println(authentication.getDetails());
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            System.out.println(authentication.getAuthorities());
            authentication.getAuthorities().forEach(role -> {
                System.out.println(role);
                System.out.println(role.getClass());
            });
        }
        return new ArrayList();
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public List history(@NotNull @RequestParam("carrier") String carrier,
                        @NotNull @RequestParam("msisdn") String msisdn,
                        Authentication authentication){

        return new ArrayList();
    }
}
