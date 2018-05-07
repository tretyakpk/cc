package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.service.CarrierServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List all(@RequestParam(value = "msisdn") String msisdn, @RequestParam(value = "csps", required = false, defaultValue = "") String csps){

        List allSubs = carrierService.getAllSubscriptions(msisdn, csps);
        logger.info("Count of subscriptions: " + allSubs.size());
        return allSubs;

    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public List allInfo(@RequestParam(value = "msisdn") String msisdn, @RequestParam(value = "csps", required = false, defaultValue = "") String csps){

        List allSubs = carrierService.getAllSubscriptionsInfo(msisdn, csps);
        logger.info("Count of subscriptions: " + allSubs.size());
        return allSubs;

    }
}
