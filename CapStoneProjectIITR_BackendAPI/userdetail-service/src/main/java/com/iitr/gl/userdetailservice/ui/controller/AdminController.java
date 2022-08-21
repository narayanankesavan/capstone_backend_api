package com.iitr.gl.userdetailservice.ui.controller;

import com.iitr.gl.userdetailservice.service.AdminService;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.ui.model.GenericRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user_detail/admin/")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/upgradeUserToAdmin")
    public String upgradeUserToAdmin(@RequestBody GenericRequestModel requestModel) {
        GenericDto dto = new GenericDto();
        dto.setUserId(requestModel.getUserId());
        HttpStatus status = adminService.upgradeUserToAdmin(dto);
        if (status == HttpStatus.OK)
            return "Successfully Updated";
        else
            return "User not found";

    }
}
