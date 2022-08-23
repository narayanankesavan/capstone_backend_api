package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.data.*;
import com.iitr.gl.userdetailservice.shared.AdminDashboardDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    XRayDetailMySqlRepository xRayDetailMySqlRepository;

    @Autowired
    PythonScriptMySqlRepository pythonScriptMySqlRepository;

    @Autowired
    PneumoniaXRayMongoDBRepository pneumoniaXRayMongoDBRepository;

    @Autowired
    TuberculosisXRayMongoDBRepository tuberculosisXRayMongoDBRepository;

    @Autowired
    PythonScriptMongoDBRepository pythonScriptMongoDBRepository;

    @Override
    public HttpStatus upgradeUserToAdmin(GenericDto dto) {
        int result = userRepository.upgradeUserToAdmin(dto.getUserId());
        if (result == 1)
            return HttpStatus.OK;
        else
            return HttpStatus.NOT_FOUND;
    }

    @Override
    public List<AdminDashboardDto> listUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<AdminDashboardDto> adminDashboardDtoList = new ArrayList<>();
        userEntityList.forEach(userEntity -> {
            AdminDashboardDto adminDashboardDto = new AdminDashboardDto();
            adminDashboardDto.setAdminUser(userEntity.getAdminUser());
            adminDashboardDto.setUserId(userEntity.getUserId());
            adminDashboardDto.setEmail(userEntity.getEmail());
            adminDashboardDto.setFirstName(userEntity.getFirstName());
            adminDashboardDto.setLastName(userEntity.getLastName());
            adminDashboardDtoList.add(adminDashboardDto);
        });
        return adminDashboardDtoList;
    }

    @Transactional
    @Override
    public HttpStatus deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity != null) {
            List<XRayDetailEntity> xRayDetailEntityList = xRayDetailMySqlRepository.findByUserId(userId);
            List<PythonScriptEntity> pythonScriptEntityList = pythonScriptMySqlRepository.findByUserId(userId);
            if (xRayDetailEntityList != null) {
                List<String> pneumoniaXrayIds = new ArrayList<>();
                List<String> tuberculosisXrayIds = new ArrayList<>();
                xRayDetailEntityList.forEach(xRayDetailEntity -> {
                    if (xRayDetailEntity.getXrayType().equalsIgnoreCase("pneumonia"))
                        pneumoniaXrayIds.add(xRayDetailEntity.getXrayId());
                    else if (xRayDetailEntity.getXrayType().equalsIgnoreCase("tuberculosis"))
                        tuberculosisXrayIds.add(xRayDetailEntity.getXrayId());
                });

                if (!pneumoniaXrayIds.isEmpty())
                    pneumoniaXRayMongoDBRepository.deleteAllUsingXrayId(pneumoniaXrayIds);
                if (!tuberculosisXrayIds.isEmpty())
                    tuberculosisXRayMongoDBRepository.deleteAllUsingXrayId(tuberculosisXrayIds);
            }

            if (pythonScriptEntityList != null) {
                List<String> pythonScriptIds = new ArrayList<>();
                pythonScriptEntityList.forEach(pythonScriptEntity -> {
                    pythonScriptIds.add(pythonScriptEntity.getScriptId());
                });

                if (!pythonScriptIds.isEmpty())
                    pythonScriptMongoDBRepository.deleteAllUsingScriptId(pythonScriptIds);
            }

            pythonScriptMySqlRepository.deleteByUserId(userId);
            xRayDetailMySqlRepository.deleteByUserId(userId);
            userRepository.deleteByUserId(userId);

            return HttpStatus.OK;
        } else
            return HttpStatus.NOT_FOUND;
    }
}
