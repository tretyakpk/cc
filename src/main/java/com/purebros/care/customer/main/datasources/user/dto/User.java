package com.purebros.care.customer.main.datasources.user.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class User {
    private Integer id;
    private String  name;
    private String  company;
    private String  email;
    private String  number;
    private Date    created_at;
    @Setter
    private ArrayList<Role> roles;
    @Setter
    private ArrayList<CSP>   csps;

    public User(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.company = user.getCompany();
        this.email = user.getEmail();
        this.number = user.getNumber();
        this.created_at = user.getCreated_at();
        this.roles = user.getRoles();
        this.csps = user.getCsps();
    }
}
