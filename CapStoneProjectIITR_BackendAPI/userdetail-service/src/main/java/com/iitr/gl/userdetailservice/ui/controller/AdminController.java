package com.iitr.gl.userdetailservice.ui.controller;

import com.iitr.gl.userdetailservice.service.AdminService;
import com.iitr.gl.userdetailservice.shared.AdminDashboardDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.ui.model.GenericRequestModel;
import com.iitr.gl.userdetailservice.util.GetJwtSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user_detail/admin/")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    Environment environment;

    @Autowired
    GetJwtSubject getJwtSubject;

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

    @PostMapping("/listUsers")
    public List<AdminDashboardDto> listUsers(@RequestHeader("Authorization") String token) {

        System.out.println("Subject : " + getJwtSubject.getSubject(token.replace("Bearer", ""), environment));

        return adminService.listUsers();
    }

    @PostMapping("/deleteUser")
    ResponseEntity<String> deleteUser(@RequestBody GenericDto dto) {
        HttpStatus httpStatus = adminService.deleteUser(dto.getUserId());

        if (HttpStatus.OK == httpStatus)
            return ResponseEntity.ok().body("Successfully deleted user");
        else if (HttpStatus.NOT_FOUND == httpStatus)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("For given userId, no user found");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body("");
    }

}
