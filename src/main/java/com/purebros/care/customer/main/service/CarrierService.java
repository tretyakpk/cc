package com.purebros.care.customer.main.service;

import java.util.List;

public interface CarrierService {

    List getAllSubscriptions(String msisdn, List<String> csps);

    List getAllSubscriptionsInfo(String msisdn, List<String> csps);

}
