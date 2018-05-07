package com.purebros.care.customer.main.service;

import com.purebros.care.customer.main.dto.CustomUserDetails;
import com.purebros.care.customer.main.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{

    private final UserService userService;

    @Autowired
    public CustomUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsernameAndPassword(String name, String password) throws UsernameNotFoundException {

        User user = userService.findUser(name, password);

        if(user == null)
            throw new UsernameNotFoundException("Username not found");

        CustomUserDetails userDetails = new CustomUserDetails(user);

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        userDetails.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        });

        return userDetails;
    }
}
