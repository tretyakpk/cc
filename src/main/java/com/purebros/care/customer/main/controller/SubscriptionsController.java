package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.service.CarrierServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public List all( @NotNull @RequestParam("msisdn") String msisdn){

        List allSubs = carrierService.getAllSubscriptions(msisdn, new ArrayList<>());
        logger.info("Count of subscriptions: " + allSubs.size());
        return allSubs;

    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public List allInfo( @NotNull @RequestParam("msisdn") String msisdn){

        List allSubs = carrierService.getAllSubscriptionsInfo(msisdn, new ArrayList<>());
        logger.info("Count of subscriptions: " + allSubs.size());
        return allSubs;

    }
}
