package com.iitr.gl.userdetailservice.ui.controller;

import com.iitr.gl.userdetailservice.service.PythonScriptService;
import com.iitr.gl.userdetailservice.service.XRayService;
import com.iitr.gl.userdetailservice.ui.model.GenericRequestModel;
import com.iitr.gl.userdetailservice.ui.model.ListUserFilesResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user_detail/list/")
public class UserDetailController {

    @Autowired
    XRayService xRayService;

    @Autowired
    PythonScriptService pythonScriptService;

    @PostMapping("/list")
    public ResponseEntity<ListUserFilesResponseModel> listUserFiles(@RequestBody GenericRequestModel requestModel) {
        ListUserFilesResponseModel listUserFilesResponseModel = xRayService.listUserFiles(requestModel.getUserId());
        if (listUserFilesResponseModel == null)
            listUserFilesResponseModel = new ListUserFilesResponseModel();
        listUserFilesResponseModel.setScripts(pythonScriptService.listUserFiles(requestModel.getUserId()));
        return ResponseEntity.ok().body(listUserFilesResponseModel);
    }
}
