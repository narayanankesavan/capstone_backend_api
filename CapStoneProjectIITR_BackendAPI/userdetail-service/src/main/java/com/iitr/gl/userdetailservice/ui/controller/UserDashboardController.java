package com.iitr.gl.userdetailservice.ui.controller;

import com.iitr.gl.userdetailservice.data.PneumoniaXRayMongoDBRepository;
import com.iitr.gl.userdetailservice.service.LoginServiceClient;
import com.iitr.gl.userdetailservice.service.UserDashboardService;
import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import com.iitr.gl.userdetailservice.ui.model.GenericRequestModel;
import com.iitr.gl.userdetailservice.ui.model.ListUserFilesResponseModel;
import com.iitr.gl.userdetailservice.ui.model.UploadXRayFileRequestModel;
import com.iitr.gl.userdetailservice.ui.model.UploadXRayFileResponseModel;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/user_detail/")
@RequiredArgsConstructor
public class UserDashboardController {
    private final PneumoniaXRayMongoDBRepository PneumoniaXRayMongoDBRepository;
    Logger loggerFactory = LoggerFactory.getLogger(UserDashboardController.class);
    @Autowired
    Environment environment;
    @Autowired
    LoginServiceClient loginServiceClient;

    @Autowired
    UserDashboardService userDashboardService;

    @GetMapping("/{id}")
    public Object getPatientDetail(@PathVariable String id) {
        return PneumoniaXRayMongoDBRepository.findById(id);
    }

    @GetMapping("/test")
    public String test() {
        return environment.getProperty("value");
    }

    @GetMapping("/testRemoteMicroService")
    public String testRemoteMicroService() {
        loggerFactory.info("Before calling loginservice microservice");
        String result = loginServiceClient.testRemote();
        loginServiceClient.testRemote();
        loggerFactory.info("After calling loginservice microservice");
        return result;
    }

    @PostMapping("/downloadXray")
    public ResponseEntity<ByteArrayResource> downloadXray(@RequestBody GenericRequestModel genericRequestModel) {
        DownloadFileDto downloadFileDto = new DownloadFileDto();
        downloadFileDto.setXrayId(genericRequestModel.getXrayId());
        downloadFileDto.setUserId(genericRequestModel.getUserId());
        DownloadFileDto file = userDashboardService.downloadXRay(downloadFileDto);
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

    @PostMapping("/viewXray")
    public ResponseEntity<String> viewXray(@RequestBody GenericRequestModel genericRequestModel) {
        DownloadFileDto downloadFileDto = new DownloadFileDto();
        downloadFileDto.setXrayId(genericRequestModel.getXrayId());
        downloadFileDto.setUserId(genericRequestModel.getUserId());
        DownloadFileDto file = userDashboardService.downloadXRay(downloadFileDto);
        if (file.getFile() != null) {
            return ResponseEntity.ok()
                    .body(Base64Utils.encodeToString(file.getFile()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(file.getErrorMessage());
        }
    }

    @PostMapping("/uploadXray")
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
        userDashboardService.uploadXRay(uploadFileDto);
        response.setMessage("xray successfully saved");
        response.setXrayId(uploadFileDto.getXrayId());
        return ResponseEntity.status(HttpStatus.OK).
                body(response);
    }

    @PostMapping("/deleteXray")
    public ResponseEntity<String> deleteXray(@RequestBody GenericRequestModel genericRequestModel) {
        GenericDto genericDto = new GenericDto();
        genericDto.setUserId(genericRequestModel.getUserId());
        genericDto.setXrayId(genericRequestModel.getXrayId());
        HttpStatus httpStatus = userDashboardService.deleteXRay(genericDto);
        if (httpStatus == HttpStatus.OK)
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("For given userId, xrayId, no xray is present");
    }

    @PostMapping("/updateXray")
    public ResponseEntity<String> updateXRay(@RequestBody UploadXRayFileRequestModel requestModel) {
        UploadFileDto uploadFileDto = new UploadFileDto();
        UploadXRayFileResponseModel response = new UploadXRayFileResponseModel();

        uploadFileDto.setUserId(requestModel.getUserId());
        uploadFileDto.setXrayId(requestModel.getXrayId());
        uploadFileDto.setFileName(requestModel.getFileName());
        uploadFileDto.setFileData(requestModel.getFileData());
        HttpStatus httpStatus = userDashboardService.updateXRay(uploadFileDto);
        if (httpStatus == HttpStatus.OK)
            return ResponseEntity.status(HttpStatus.OK).body("Updated successfully");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("For given userId, xrayId, no xray is present");
    }

    @PostMapping("/list")
    public ResponseEntity<ListUserFilesResponseModel> listUserFiles(@RequestBody GenericRequestModel requestModel) {
        return ResponseEntity.ok().body(
                userDashboardService.listUserFiles(requestModel.getUserId()));
    }


    /*@GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        DownloadXRayDto file = patientXRayDownloadService.downloadFile("62af038a54091e5445b857b1");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))bv
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFile()));
    }*/

    @PostMapping("/getToken")
    public void getToken(@RequestHeader("Authorization") String token) {
        System.out.println("Token : " + token);
    }
}
