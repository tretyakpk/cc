package com.purebros.care.customer.main.service;

import com.purebros.care.customer.main.datasources.user.dao.UserDao;
import com.purebros.care.customer.main.datasources.user.dto.CustomUserDetails;
import com.purebros.care.customer.main.datasources.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{

    private final UserDao userDao;

    @Autowired
    public CustomUserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public CustomUserDetails loadUserByUsernameAndPasoword(String name, String password) throws UsernameNotFoundException {

        Optional<User> optionalUser = userDao.findUser(name, password);

        if(!optionalUser.isPresent())
            throw new UsernameNotFoundException("Username not found");

        CustomUserDetails userDetails = optionalUser.map(CustomUserDetails::new).get();

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        userDetails.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        });

        return userDetails;
    }
}
