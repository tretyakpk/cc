package com.purebros.care.customer.main.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UnsubscriptionServiceImpl implements UnsubscriptionService {

    private final static Logger logger = LoggerFactory.getLogger(UnsubscriptionServiceImpl.class);

    @Override
    public String deactivate(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            return "Success: return code: '" + result + "'";
        } catch (HttpServerErrorException e) {
            return "Service temporary unavailable please try again later: '" + e.getMessage() + "'";
        }
    }
}
