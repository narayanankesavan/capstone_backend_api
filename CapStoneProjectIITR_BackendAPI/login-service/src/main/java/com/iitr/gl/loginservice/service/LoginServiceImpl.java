package com.iitr.gl.loginservice.service;

import com.iitr.gl.loginservice.data.MySqlLoginRepository;
import com.iitr.gl.loginservice.data.UserEntity;
import com.iitr.gl.loginservice.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    MySqlLoginRepository mySqlLoginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = mySqlLoginRepository.findByEmail(username);

        if (userEntity == null)
            throw new UsernameNotFoundException(username);
        else
            return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = mySqlLoginRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);
        else
            return new ModelMapper().map(userEntity, UserDto.class);

    }
}
