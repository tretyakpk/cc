package com.purebros.care.customer.main.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter @Getter
@Service
public class CarrierServiceImpl implements CarrierService {


    private final Environment env;

    @Autowired
    public CarrierServiceImpl(Environment env) {
        this.env = env;
    }

    @Override
    public List getAllSubscriptions(String carrier, String msisdn) {

         return null;
    }
}
