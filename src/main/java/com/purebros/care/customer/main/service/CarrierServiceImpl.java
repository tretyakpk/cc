package com.purebros.care.customer.main.service;

import com.purebros.care.customer.main.dto.SubscriptionsDto;
import com.purebros.care.customer.main.dto.SubscriptionsInfDto;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter @Getter
@Service
public class CarrierServiceImpl implements CarrierService {

    private final static Logger logger = LoggerFactory.getLogger(CarrierServiceImpl.class);

    private static final String ROUTINE_DATA_STORAGE = "#result-set-1";

    private final DataSource dataSource;

    @Autowired
    public CarrierServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * Function returns all {@link SubscriptionsDto} for one msisdn for passed Content service providers
     * using stored procedure
     *
     * @param msisdn String
     * @param csps List[String]
     * @return List[SubscriptionsDto] (sorted by subscription start timestamp desc) || null
     */
    @Override
    public List getAllSubscriptions(String msisdn, String csps){
        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(dataSource);

        procedure.setSql("CC_getUserServiceSubscriptions");
        procedure.declareParameter(new SqlParameter("in_msisdn", Types.VARCHAR));
        procedure.declareParameter(new SqlParameter("in_providersList", Types.VARCHAR));

        Map<String, Object> result = procedure.execute(msisdn, csps);
        ArrayList res = (ArrayList) result.get(ROUTINE_DATA_STORAGE);

        logger.info("--- fetched subscriptions for msisdn 39" + msisdn + ": " + res.size());

        List<SubscriptionsDto> subscriptions = new ArrayList<>();

        res.forEach(v -> {
            SubscriptionsDto sub = null;

            sub = SubscriptionsDto.builder()
                    .account(                  (String) ((LinkedCaseInsensitiveMap) v).get("account"))
                    .serviceName(              (String) ((LinkedCaseInsensitiveMap) v).get("Service"))
                    .providerName(             (String) ((LinkedCaseInsensitiveMap) v).get("CSP"))
                    .subscriptionStart(         (Timestamp) ((LinkedCaseInsensitiveMap) v).get("SubscriptionStart"))
                    .subscriptionEnd(           (Timestamp) ((LinkedCaseInsensitiveMap) v).get("SubscriptionEnd"))
                    .active( (                  (Integer) ((LinkedCaseInsensitiveMap) v).get("StateTITSrvSta")) == 0)
                    .unsubscriptionUrl((new String((byte[]) ((LinkedCaseInsensitiveMap) v).get("unsubscription_URL"), StandardCharsets.US_ASCII)))
                    .build();

            subscriptions.add(sub);
        });

         return subscriptions;
    }

    /**
     * Function returns all {@link SubscriptionsInfDto} for one msisdn for passed Content service providers
     * using stored procedure
     *
     * @param msisdn String
     * @param csps List[String]
     * @return List[SubscriptionsDto] (sorted by subscription start timestamp desc) || null
     */
    @Override
    public List getAllSubscriptionsInfo(String msisdn, String csps) {
        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(dataSource);
        procedure.setSql("CC_getUserBillings");
        procedure.declareParameter(new SqlParameter("in_msisdn", Types.VARCHAR));
        procedure.declareParameter(new SqlParameter("in_providersList", Types.VARCHAR));

        // add international prefix to stored procedure that requires number with this prefix
        msisdn = "39" + msisdn;

        Map<String, Object> result = procedure.execute(msisdn, csps);
        ArrayList res = (ArrayList) result.get(ROUTINE_DATA_STORAGE);

        logger.info("--- fetched information of subscriptions for msisdn " + msisdn + ": " + res.size());

        List<SubscriptionsInfDto> subscriptions = new ArrayList<>();
        res.forEach(v -> {
            SubscriptionsInfDto.SubscriptionsInfDtoBuilder builder = SubscriptionsInfDto.builder()
                    .account(                  (String) ((LinkedCaseInsensitiveMap) v).get("account"))
                    .providerName(             (String) ((LinkedCaseInsensitiveMap) v).get("CSP"))
                    .serviceName(              (String) ((LinkedCaseInsensitiveMap) v).get("serviceName"))
                    .operationTime(            (Timestamp) ((LinkedCaseInsensitiveMap) v).get("DateTimePostCont"))
                    .chargeAmount((Long) ((LinkedCaseInsensitiveMap) v).get("chargeAmount"))
                    .msgText(                  (String) ((LinkedCaseInsensitiveMap) v).get("msg_text"))
                    .billingStatus(            (String) ((LinkedCaseInsensitiveMap) v).get("billing_status"))
                    .refundUrl(                (String) ((LinkedCaseInsensitiveMap) v).get("refund_URL"));

            subscriptions.add(builder.build());
        });

        return subscriptions;
    }
}
