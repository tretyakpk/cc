package com.purebros.care.customer.main.service;

import java.io.IOException;
import java.util.List;

public interface CarrierService {

    List getAllSubscriptions(String msisdn, String csps);

    List getAllSubscriptionsInfo(String msisdn, String csps);

}
