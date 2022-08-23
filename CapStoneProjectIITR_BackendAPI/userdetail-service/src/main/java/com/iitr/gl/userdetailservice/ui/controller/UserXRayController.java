package com.iitr.gl.userdetailservice.ui.controller;

import com.iitr.gl.userdetailservice.service.LoginServiceClient;
import com.iitr.gl.userdetailservice.service.XRayService;
import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import com.iitr.gl.userdetailservice.ui.model.GenericRequestModel;
import com.iitr.gl.userdetailservice.ui.model.UploadXRayFileRequestModel;
import com.iitr.gl.userdetailservice.ui.model.UploadXRayFileResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user_detail/xray/")
public class UserXRayController {
    Logger loggerFactory = LoggerFactory.getLogger(UserXRayController.class);
    @Autowired
    Environment environment;
    @Autowired
    LoginServiceClient loginServiceClient;

    @Autowired
    XRayService XRayService;

    @GetMapping("/testRemoteMicroService")
    public String testRemoteMicroService() {
        loggerFactory.info("Before calling loginservice microservice");
        String result = loginServiceClient.testRemote();
        loginServiceClient.testRemote();
        loggerFactory.info("After calling loginservice microservice");
        return result;
    }

    @PostMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadXray(@RequestBody GenericRequestModel genericRequestModel) {
        DownloadFileDto downloadFileDto = new DownloadFileDto();
        downloadFileDto.setXrayId(genericRequestModel.getXrayId());
        downloadFileDto.setUserId(genericRequestModel.getUserId());
        DownloadFileDto file = XRayService.downloadXRay(downloadFileDto);
        if (file.getFile() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/jpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(new ByteArrayResource(file.getFile()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping("/view")
    public ResponseEntity<String> viewXray(@RequestBody GenericRequestModel genericRequestModel) {
        DownloadFileDto downloadFileDto = new DownloadFileDto();
        downloadFileDto.setXrayId(genericRequestModel.getXrayId());
        downloadFileDto.setUserId(genericRequestModel.getUserId());
        DownloadFileDto file = XRayService.downloadXRay(downloadFileDto);
        if (file.getFile() != null) {
            return ResponseEntity.ok()
                    .body(Base64Utils.encodeToString(file.getFile()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(file.getErrorMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadXRayFileResponseModel> uploadXray(@RequestBody UploadXRayFileRequestModel requestModel) {
        UploadFileDto uploadFileDto = new UploadFileDto();
        UploadXRayFileResponseModel response = new UploadXRayFileResponseModel();

        if (!requestModel.getXrayType().equalsIgnoreCase("pneumonia") &&
                !requestModel.getXrayType().equalsIgnoreCase("tuberculosis")) {
            response.setMessage("Invalid xray type");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).
                    body(response);
        }
        uploadFileDto.setXrayType(requestModel.getXrayType());
        uploadFileDto.setUserId(requestModel.getUserId());
        uploadFileDto.setXrayId(UUID.randomUUID().toString());
        uploadFileDto.setFileName(requestModel.getFileName());
        uploadFileDto.setFileData(requestModel.getFileData());
        XRayService.uploadXRay(uploadFileDto);
        response.setMessage("xray successfully saved");
        response.setXrayId(uploadFileDto.getXrayId());
        return ResponseEntity.status(HttpStatus.OK).
                body(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteXray(@RequestBody GenericRequestModel genericRequestModel) {
        GenericDto genericDto = new GenericDto();
        genericDto.setUserId(genericRequestModel.getUserId());
        genericDto.setXrayId(genericRequestModel.getXrayId());
        HttpStatus httpStatus = XRayService.deleteXRay(genericDto);
        if (httpStatus == HttpStatus.OK)
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("For given userId, xrayId, no xray is present");
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAllXray(@RequestBody GenericRequestModel requestModel) {
        HttpStatus httpStatus = XRayService.deleteAllXRay(requestModel.getUserId());
        if (HttpStatus.OK == httpStatus)
            return ResponseEntity.status(HttpStatus.OK).
                    body("Successfully deleted");
        else if (HttpStatus.NOT_FOUND == httpStatus)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("XRays with given userId are not found");
        return null;
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateXray(@RequestBody UploadXRayFileRequestModel requestModel) {
        UploadFileDto uploadFileDto = new UploadFileDto();
        UploadXRayFileResponseModel response = new UploadXRayFileResponseModel();

        uploadFileDto.setUserId(requestModel.getUserId());
        uploadFileDto.setXrayId(requestModel.getXrayId());
        uploadFileDto.setFileName(requestModel.getFileName());
        uploadFileDto.setFileData(requestModel.getFileData());
        HttpStatus httpStatus = XRayService.updateXRay(uploadFileDto);
        if (httpStatus == HttpStatus.OK)
            return ResponseEntity.status(HttpStatus.OK).body("Updated successfully");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("For given userId, xrayId, no xray is present");
    }

    @PostMapping("/getToken")
    public void getToken(@RequestHeader("Authorization") String token) {
        System.out.println("Token : " + token);
    }
}
