package com.purebros.care.customer.main.service;

import com.purebros.care.customer.main.datasources.wind.dto.SubscriptionsDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;

@Setter @Getter
@Service
public class CarrierServiceImpl implements CarrierService {

    private static final String WIND = "wind";
    private static final String VODA = "vodafone";
    private static final String TIM  = "tim";
    private static final String H3G  = "h3g";

    private final DataSource windDataSource;
    private final DataSource vodafoneDataSource;
    private final DataSource timDataSource;
    private final DataSource h3gDataSource;

    @Autowired
    public CarrierServiceImpl(@Qualifier("windDataSource") DataSource windDataSource,
                              @Qualifier("vodafoneDataSource") DataSource vodaDataSource,
                              @Qualifier("timDataSource") DataSource timDataSource,
                              @Qualifier("h3gDataSource") DataSource h3gDataSource
    ) {
        this.windDataSource = windDataSource;
        this.vodafoneDataSource = vodaDataSource;
        this.timDataSource = timDataSource;
        this.h3gDataSource = h3gDataSource;
    }

    @Override
    public List<SubscriptionsDto> getAllSubscriptions(String carrier, String msisdn) {
        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(getDataSource(carrier));

        procedure.setSql("CC_getUserServiceSubscriptions");
        procedure.declareParameter(new SqlParameter("in_msisdn", Types.VARCHAR));

        Map<String, Object> result = procedure.execute(msisdn);

        ArrayList res = (ArrayList) result.get("#result-set-1");

        List<SubscriptionsDto> subscriptions = new ArrayList<>();
        res.forEach(v -> {
            SubscriptionsDto sub = SubscriptionsDto.builder()
                    .serviceName(              (String) ((LinkedCaseInsensitiveMap) v).get("Service"))
                    .providerName(             (String) ((LinkedCaseInsensitiveMap) v).get("ProviderName"))
                    .subscriptionStart(getDate((String) ((LinkedCaseInsensitiveMap) v).get("SubscriptionStart")))
                    .subscriptionEnd(  getDate((String) ((LinkedCaseInsensitiveMap) v).get("SubscriptionEnd")))
                    .active( (                   (Long) ((LinkedCaseInsensitiveMap) v).get("State")) == 0)
                    .build();
            subscriptions.add(sub);
        });

        return subscriptions;
    }

    private DataSource getDataSource(String carrierName) throws RuntimeException{
        switch (carrierName){
            case WIND:
                return windDataSource;
            case VODA:
                return vodafoneDataSource;
            case TIM:
                return timDataSource;
            case H3G:
                return h3gDataSource;
            default: throw new RuntimeException("not found dataSource for " + carrierName);
        }
    }

    private Date getDate(String dateString){
        try {
            return new DateFormatter("yyyy-MM-dd HH:mm:ss").parse(dateString, Locale.ITALY);
        } catch (Exception e) {
            return null;
        }
    }
}
