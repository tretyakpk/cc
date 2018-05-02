package com.purebros.care.customer.main.datasources.wind.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date   subscriptionStart;

    @Nullable
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date   subscriptionEnd;
    private boolean active;

}
