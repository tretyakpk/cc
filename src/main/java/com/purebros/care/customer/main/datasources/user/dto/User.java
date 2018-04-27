package com.purebros.care.customer.main.datasources.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@Builder
@ToString
public class User {
    private Integer id;
    private String  name;
    private String  company;
    private String  email;
    private String  number;
    private Date    created_at;
}
