package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.data.UserRepository;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserRepository userRepository;

    @Override
    public HttpStatus upgradeUserToAdmin(GenericDto dto) {
        int result = userRepository.upgradeUserToAdmin(dto.getUserId());
        if (result == 1)
            return HttpStatus.OK;
        else
            return HttpStatus.NOT_FOUND;
    }
}
