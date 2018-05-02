package com.purebros.care.customer.main.component;

import com.purebros.care.customer.main.datasources.user.dao.UserDao;
import com.purebros.care.customer.main.datasources.user.dto.CustomUserDetails;
import com.purebros.care.customer.main.datasources.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDao userDao;

    @Autowired
    public CustomAuthenticationProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name     = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println(name);
        System.out.println(password);

        Optional<User> optionalUser = userDao.findUser(name, password);

        if(!optionalUser.isPresent())
            throw new UsernameNotFoundException("Username not found");

        CustomUserDetails customUserDetails = optionalUser.map(CustomUserDetails::new).get();

        System.out.println("custom user details: ");
        System.out.println(customUserDetails);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        optionalUser.ifPresent(user -> user.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }));

        return new UsernamePasswordAuthenticationToken(name, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
