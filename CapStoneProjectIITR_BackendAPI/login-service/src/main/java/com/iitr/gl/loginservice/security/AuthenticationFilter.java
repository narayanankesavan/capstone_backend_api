package com.iitr.gl.loginservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iitr.gl.loginservice.service.LoginService;
import com.iitr.gl.loginservice.shared.UserDto;
import com.iitr.gl.loginservice.ui.model.UserLoginDetailRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    LoginService loginService;
    Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager, LoginService loginService, Environment environment) {
        this.environment = environment;
        this.loginService = loginService;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginDetailRequest credential = new ObjectMapper().
                    readValue(request.getInputStream(), UserLoginDetailRequest.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(credential.getEmail(),
                            credential.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String userEmail = ((User) (authResult.getPrincipal())).getUsername();
        System.out.println(userEmail + " &&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        UserDto userDto = loginService.getUserByEmail(userEmail);

        String jwtToken = Jwts.builder().
                setSubject(userDto.getUserId()).
                setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time")))).
                signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret")).
                compact();

        response.setHeader("authToken", jwtToken);
        response.setHeader("userId", userDto.getUserId());
    }
}
