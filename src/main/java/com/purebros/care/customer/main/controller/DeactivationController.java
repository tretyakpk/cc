package com.purebros.care.customer.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping(value = "/deactivate")
public class DeactivationController {

    private final static Logger logger = LoggerFactory.getLogger(DeactivationController.class);

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String all(@RequestParam Integer subscriptionId){

        return (subscriptionId != 0) ? "true" : "false";
    }
}
