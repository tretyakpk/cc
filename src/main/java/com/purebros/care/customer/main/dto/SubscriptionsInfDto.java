package com.purebros.care.customer.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Builder @Setter @Getter
public class SubscriptionsInfDto {

    private String account;

    private String providerName;

    private String serviceName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operationTime;

    private Float chargeAmount;

    private String msgText;

    private String billingStatus;

    private String refundUrl;
}
