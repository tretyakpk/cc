package com.purebros.care.customer.main.datasources.user.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
public class CustomUserDetails extends User implements UserDetails {

    private List<GrantedAuthority> authorities;
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public CustomUserDetails(final User user){
        super(user);
        this.username = user.getName();
    }
}
