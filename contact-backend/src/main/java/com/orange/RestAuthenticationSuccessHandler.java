package com.orange;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final int expirationTime;
    private final String secret;

    public RestAuthenticationSuccessHandler(@Value("${jwt.expirationTime}") int expirationTime,
                                            @Value("${jwt.secret}") String secret) {
        this.expirationTime = expirationTime;
        this.secret = secret;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        
        
        
        for (GrantedAuthority grantedAuthority : principal.getAuthorities()) {
        	grantedAuthority.getAuthority();
		}
        
        // TODO refactor
        List<String> collect = principal.getAuthorities().stream().map(a-> a.getAuthority()).collect(Collectors.toList());
        String[] array = collect.toArray(new String[collect.size()]);
        
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withArrayClaim("roles", array)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))              
                .sign(Algorithm.HMAC256(secret));
        response.getOutputStream().print("{\"token\": \"" + token + "\"}");
    }
}