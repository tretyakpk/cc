package com.purebros.care.customer.main.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("error_mapping_unsubscroption_servlet.properties")
public class UnsubscriptionServiceImpl implements UnsubscriptionService {

    private final Environment env;

    private final static Logger logger = LoggerFactory.getLogger(UnsubscriptionServiceImpl.class);

    public UnsubscriptionServiceImpl(Environment env) {
        this.env = env;
    }

    @Override
    public String deactivate(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            return "Return code: " + result + ". Message: '" + env.getProperty(result) + "'";
        } catch (HttpServerErrorException e) {
            return "Service temporary unavailable please try again later: '" + e.getMessage() + "'";
        }
    }
}
