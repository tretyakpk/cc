package com.purebros.care.customer.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Setter @Getter
@ToString
public class SubscriptionsInfDto {

    private String account;

    private String providerName;

    private String serviceName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operationTime;

    private Integer chargeAmount;

    private String msgText;

    private String billingStatus;

    private String refundUrl;
}
