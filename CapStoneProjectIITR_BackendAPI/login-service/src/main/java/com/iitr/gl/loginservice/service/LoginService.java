package com.iitr.gl.loginservice.service;

import com.iitr.gl.loginservice.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {

    public UserDto getUserByEmail(String email);
}
