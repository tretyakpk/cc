package com.purebros.care.customer.main.datasources.wind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;

@AllArgsConstructor
@Builder @Setter @Getter
public class SubscriptionsDto {

    private String providerName;
    private String serviceName;
    private Date   subscriptionStart;

    @Nullable
    private Date   subscriptionEnd;
    private boolean active;

}
