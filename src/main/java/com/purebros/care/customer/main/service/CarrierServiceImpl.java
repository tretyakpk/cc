package com.purebros.care.customer.main.service;

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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

@Setter @Getter
@Service
public class CarrierServiceImpl implements CarrierService {

    private final static Logger logger = LoggerFactory.getLogger(CarrierServiceImpl.class);

    private final Environment env;

    private final Integer offset;

    @Autowired
    public CarrierServiceImpl(Environment env) {
        this.env = env;
        this.offset = LocalDateTime.now().atZone(ZoneId.of(this.env.getProperty("timezone"))).getOffset().getTotalSeconds() * 1000;
    }

    @Override
    public SubscriptionsDto[] getAllSubscriptions(String msisdn, String carrier, CustomUserDetails userDetails){

        String url = env.getProperty("servlet" + "." + carrier.toLowerCase()) + "/subscriptions";

        StringBuilder cspsCsv = new StringBuilder();
        if(userDetails.getCsps() != null) userDetails.getCsps().forEach(csp -> cspsCsv.append(csp.getId()).append(","));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("msisdn", msisdn)
                .queryParam("csps", cspsCsv);

        RestTemplate restTemplate = new RestTemplate();
        SubscriptionsDto[] subscriptionsDtos = restTemplate.getForObject(builder.toUriString(), SubscriptionsDto[].class);

        Arrays.sort(subscriptionsDtos, Comparator.comparing(SubscriptionsDto::getSubscriptionStart, Comparator.reverseOrder()));

        Arrays.stream(subscriptionsDtos).forEach(sub -> {
            if(sub.getSubscriptionStart() != null)
                sub.setSubscriptionStart(new Date((sub.getSubscriptionStart().getTime() + offset)));
            if(sub.getSubscriptionEnd() != null)
                sub.setSubscriptionEnd(new Date((sub.getSubscriptionEnd().getTime() + offset)));
        });

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

        Arrays.stream(subscriptionsInfDtos).forEach(sub -> {
            if (sub.getOperationTime() != null)
                sub.setOperationTime(new Date((sub.getOperationTime().getTime() + offset)));
        });

        logger.info(userDetails.getName() + ": Find subscriptions information: " + subscriptionsInfDtos.length + "; carrier: " + carrier + "; msisdn: " + msisdn);

        return subscriptionsInfDtos;
    }
}
