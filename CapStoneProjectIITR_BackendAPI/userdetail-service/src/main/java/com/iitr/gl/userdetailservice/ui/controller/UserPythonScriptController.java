package com.iitr.gl.userdetailservice.ui.controller;

import com.iitr.gl.userdetailservice.service.PythonScriptService;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import com.iitr.gl.userdetailservice.ui.model.UploadPythonScriptRequestModel;
import com.iitr.gl.userdetailservice.ui.model.UploadPythonScriptResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user_detail/python_script/")
public class UserPythonScriptController {

    @Autowired
    PythonScriptService pythonScriptService;

    @PostMapping("/upload")
    public ResponseEntity<UploadPythonScriptResponseModel> uploadPythonScript(@RequestBody UploadPythonScriptRequestModel requestModel)
    {
        UploadFileDto fileDto = new UploadFileDto();
        fileDto.setFileName(requestModel.getFileName());
        fileDto.setFileData(requestModel.getData());
        fileDto.setScriptId(UUID.randomUUID().toString());
        fileDto.setUserId(requestModel.getUserId());
        pythonScriptService.uploadPythonScript(fileDto);
        UploadPythonScriptResponseModel response = new UploadPythonScriptResponseModel();
        response.setMessage("script successfully saved");
        response.setScriptId(fileDto.getScriptId());
        return ResponseEntity.status(HttpStatus.OK).
                body(response);
    }
}
