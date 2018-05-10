package com.purebros.care.customer.main.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RefundServiceImpl implements RefundService {

    @Override
    public String refund(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            return "Return code: '" + result + "'";
        } catch (HttpServerErrorException e) {
            return "Service temporary unavailable please try again later: '" + e.getMessage() + "'";
        } catch (Exception e) {
            return "Something went wrong: '" + e.getMessage() + "'";
        }
    }
}
