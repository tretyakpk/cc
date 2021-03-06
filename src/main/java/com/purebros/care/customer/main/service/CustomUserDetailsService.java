package com.purebros.care.customer.main.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService{
    UserDetails loadUserByUsernameAndPassword(String userName, String password) throws UsernameNotFoundException;
}
