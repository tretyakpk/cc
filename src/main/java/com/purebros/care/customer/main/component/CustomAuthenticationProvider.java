package com.purebros.care.customer.main.component;

import com.purebros.care.customer.main.dto.CustomUserDetails;
import com.purebros.care.customer.main.service.CustomUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    public CustomAuthenticationProvider(CustomUserDetailsServiceImpl customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name     = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsernameAndPassword(name, password);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        userDetails.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        });

//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + "GET_USER_SERVICE_SUBSCRIPTIONS"));
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + "SERVICE_UNSUBSCRIPTION"));
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + "GET_USER_BILLINGS"));
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + "VODAFONE_REFUND"));

        userDetails.setAuthorities(grantedAuthorities);

        logger.info("User successfully login: " + name + "; roles: " + userDetails.getRoles() + "; csps: " + userDetails.getCsps());

        return new UsernamePasswordAuthenticationToken(userDetails, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals( UsernamePasswordAuthenticationToken.class);
    }
}
