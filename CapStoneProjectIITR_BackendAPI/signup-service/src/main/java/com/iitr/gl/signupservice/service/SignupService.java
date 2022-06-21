package com.iitr.gl.signupservice.service;

import com.iitr.gl.signupservice.shared.UserDto;

public interface SignupService {

    public UserDto createUser(UserDto userDetails);
}
