package com.purebros.care.customer.main.service;

import com.purebros.care.customer.main.datasources.user.dao.UserDao;
import com.purebros.care.customer.main.datasources.user.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println("load by username: " + s);

        CustomUserDetails user = (CustomUserDetails) userDao.findUser(s, s);

        if(user == null)
            throw new UsernameNotFoundException("Bad credentials");
        else
            return user;
    }
}
