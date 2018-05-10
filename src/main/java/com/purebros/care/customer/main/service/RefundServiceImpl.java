package com.purebros.care.customer.main.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

@Service
public class RefundServiceImpl implements RefundService {


    private final static Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);

    @Override
    public String refund(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            return "Return code: '" + result + "'";
        } catch (HttpServerErrorException e) {
            logger.error(e.getMessage());
            return "Service temporary unavailable please try again later: '" + e.getMessage() + "'";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "Something went wrong! Please try again later.";
        }
    }
}
