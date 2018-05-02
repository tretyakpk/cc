package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.datasources.user.dto.CustomUserDetails;
import com.purebros.care.customer.main.service.CarrierServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final static Logger logger = LoggerFactory.getLogger(SubscriptionsController.class);

    private final CarrierServiceImpl carrierService;

    @Autowired
    public SubscriptionsController(CarrierServiceImpl carrierService) {
        this.carrierService = carrierService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<List> all(@NotNull @RequestParam("carrier") String carrier,
                                   @NotNull @RequestParam("msisdn") String msisdn,
                                   Authentication authentication){

        logger.info(carrier);
        logger.info(msisdn);

        if(authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            logger.info("find user: " + userDetails.getUsername());
        } else {
            logger.info("user not found!");
        }

        try {
            List allSubs = carrierService.getAllSubscriptions(carrier.toLowerCase(), msisdn);
            logger.info("Count of subscriptions: " + allSubs.size());
            return new ResponseEntity<>(allSubs, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
