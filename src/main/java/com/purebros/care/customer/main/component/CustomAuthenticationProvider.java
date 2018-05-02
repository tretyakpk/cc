package com.purebros.care.customer.main.component;

import com.purebros.care.customer.main.datasources.user.dto.CustomUserDetails;
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

import java.util.HashSet;
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

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsernameAndPasoword(name, password);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        userDetails.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        });

        logger.info(String.format("User successfully login: %s", name));

        return new UsernamePasswordAuthenticationToken(userDetails, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
