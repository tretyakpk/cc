package com.purebros.care.customer.main.service;

import com.purebros.care.customer.main.dto.CSP;
import com.purebros.care.customer.main.dto.CustomUserDetails;
import com.purebros.care.customer.main.dto.SubscriptionsDto;
import com.purebros.care.customer.main.dto.SubscriptionsInfDto;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

@Setter @Getter
@Service
public class CarrierServiceImpl implements CarrierService {

    private final static Logger logger = LoggerFactory.getLogger(CarrierServiceImpl.class);

    private final Environment env;

    @Autowired
    public CarrierServiceImpl(Environment env) {
        this.env = env;
    }

    @Override
    public SubscriptionsDto[] getAllSubscriptions(String msisdn, String carrier, CustomUserDetails userDetails) {

        String url = env.getProperty("servlet" + "." + carrier.toLowerCase()) + "/subscriptions";

        StringBuilder cspsCsv = new StringBuilder();
        if(userDetails.getCsps() != null) userDetails.getCsps().forEach(csp -> cspsCsv.append(csp.getId()).append(","));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("msisdn", msisdn)
                .queryParam("csps", cspsCsv);

        RestTemplate restTemplate = new RestTemplate();
        SubscriptionsDto[] subscriptionsDtos = restTemplate.getForObject(builder.toUriString(), SubscriptionsDto[].class);

        Arrays.sort(subscriptionsDtos, Comparator.comparing(SubscriptionsDto::getSubscriptionStart, Comparator.reverseOrder()));

        logger.info(userDetails.getName() + ": find subscriptions: " + subscriptionsDtos.length + "; carrier: " + carrier + "; msisdn: " + msisdn);

        return subscriptionsDtos;
    }

    @Override
    public SubscriptionsInfDto[] getAllSubscriptionsInfo(String msisdn, String carrier, CustomUserDetails userDetails) {

        String url = env.getProperty("servlet" + "." + carrier.toLowerCase()) + "/subscriptions/info";

        StringBuilder cspsCsv = new StringBuilder();
        if(userDetails.getCsps() != null) userDetails.getCsps().forEach(csp -> cspsCsv.append(csp.getId()).append(","));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("msisdn", msisdn)
                .queryParam("csps", cspsCsv);

        RestTemplate restTemplate = new RestTemplate();
        SubscriptionsInfDto[] subscriptionsInfDtos = restTemplate.getForObject(builder.toUriString(), SubscriptionsInfDto[].class);

        Arrays.sort(subscriptionsInfDtos, Comparator.comparing(SubscriptionsInfDto::getOperationTime, Comparator.reverseOrder()));

        logger.info(userDetails.getName() + ": find subscriptions information: " + subscriptionsInfDtos.length + "; carrier: " + carrier + "; msisdn: " + msisdn);

        return subscriptionsInfDtos;
    }
}
