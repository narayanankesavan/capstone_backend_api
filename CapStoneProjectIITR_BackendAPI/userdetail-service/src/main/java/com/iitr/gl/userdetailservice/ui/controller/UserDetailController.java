package com.iitr.gl.userdetailservice.ui.controller;

import com.iitr.gl.userdetailservice.service.PythonScriptService;
import com.iitr.gl.userdetailservice.service.XRayService;
import com.iitr.gl.userdetailservice.ui.model.GenericRequestModel;
import com.iitr.gl.userdetailservice.ui.model.ListUserFilesResponseModel;
import com.iitr.gl.userdetailservice.util.GetJwtSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_detail/user/")
public class UserDetailController {

    @Autowired
    XRayService xRayService;

    @Autowired
    PythonScriptService pythonScriptService;

    @Autowired
    Environment environment;

    @Autowired
    GetJwtSubject getJwtSubject;

    @PostMapping("/userFiles")
    public ResponseEntity<ListUserFilesResponseModel> listUserFiles(@RequestBody GenericRequestModel requestModel,
                                                                    @RequestHeader("Authorization") String token) {
        getJwtSubject.verifyIfAuthorized(token, requestModel.getUserId(), environment, false);
        ListUserFilesResponseModel listUserFilesResponseModel = xRayService.listUserFiles(requestModel.getUserId());
        if (listUserFilesResponseModel == null)
            listUserFilesResponseModel = new ListUserFilesResponseModel();
        listUserFilesResponseModel.setScripts(pythonScriptService.listUserFiles(requestModel.getUserId()));
        return ResponseEntity.ok().body(listUserFilesResponseModel);
    }
}
