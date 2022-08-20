package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.shared.GenericDto;
import org.springframework.http.HttpStatus;

public interface AdminService {

    public HttpStatus upgradeUserToAdmin(GenericDto dto);
}
