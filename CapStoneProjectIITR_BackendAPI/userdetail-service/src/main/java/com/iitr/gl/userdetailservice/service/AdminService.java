package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.shared.AdminDashboardDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface AdminService {
    HttpStatus upgradeUserToAdmin(GenericDto dto);

    List<AdminDashboardDto> listUsers();

    HttpStatus deleteUser(String userId);
}
