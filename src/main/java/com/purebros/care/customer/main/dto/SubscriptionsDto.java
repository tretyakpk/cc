package com.purebros.care.customer.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Setter @Getter
@ToString
public class SubscriptionsDto {

    private String account;

    private String providerName;

    private String serviceName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date   subscriptionStart;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date   subscriptionEnd;

    private boolean active;

    private String unsubscriptionUrl;
}
