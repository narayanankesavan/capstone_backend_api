package com.iitr.gl.patientdetailservice.ui.controller;

import com.iitr.gl.patientdetailservice.data.PatientXRayMongoDBRepository;
import com.iitr.gl.patientdetailservice.service.LoginServiceClient;
import com.iitr.gl.patientdetailservice.service.PatientDetailServiceImpl;
import com.iitr.gl.patientdetailservice.shared.DownloadXRayDto;
import com.iitr.gl.patientdetailservice.shared.UploadXRayDetailDto;
import com.iitr.gl.patientdetailservice.ui.model.DownloadXRayRequestModel;
import com.iitr.gl.patientdetailservice.ui.model.PatientXRayDetails;
import com.iitr.gl.patientdetailservice.ui.model.UploadXRayRequestModel;
import com.iitr.gl.patientdetailservice.ui.model.UploadXRayResponseModel;
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
@RequestMapping("/patient_detail/")
@RequiredArgsConstructor
public class PatientXRayController {
    private final PatientXRayMongoDBRepository patientXRayMongoDBRepository;
    Logger loggerFactory = LoggerFactory.getLogger(PatientXRayController.class);
    @Autowired
    Environment environment;
    @Autowired
    LoginServiceClient loginServiceClient;

    @Autowired
    PatientDetailServiceImpl patientDetailServiceImpl;

    @PostMapping("/addDetail")
    public void addXRayDetail(@RequestBody PatientXRayDetails patientXRayDetails) {
        //patientXRayMongoDBRepository.save(patientXRayDetails);
    }

    @GetMapping("/{id}")
    public Object getPatientDetail(@PathVariable String id) {
        return patientXRayMongoDBRepository.findById(id);
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
    public ResponseEntity<ByteArrayResource> downloadXray(@RequestBody DownloadXRayRequestModel downloadXRayRequestModel)
    {

        DownloadXRayDto file = patientDetailServiceImpl.downloadXray(downloadXRayRequestModel);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFile()));
    }

    @PostMapping("/viewXray")
    public ResponseEntity<String> viewXray(@RequestBody DownloadXRayRequestModel downloadXRayRequestModel)
    {
        DownloadXRayDto file = patientDetailServiceImpl.downloadXray(downloadXRayRequestModel);
        return ResponseEntity.ok()
                .body(Base64Utils.encodeToString(file.getFile()));
    }

    @PostMapping("/uploadXray")
    public ResponseEntity<UploadXRayResponseModel> uploadXray(@RequestBody UploadXRayRequestModel requestModel)
    {
        UploadXRayDetailDto uploadXRayDetailDto = new UploadXRayDetailDto();
        UploadXRayResponseModel response = new UploadXRayResponseModel();

        if(!requestModel.getXrayType().equalsIgnoreCase("pneumonia")) {
            response.setMessage("Invalid xray type");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).
                    body(response);
        }
        uploadXRayDetailDto.setXrayType(requestModel.getXrayType());
        uploadXRayDetailDto.setUserId(requestModel.getUserId());
        uploadXRayDetailDto.setXrayId(UUID.randomUUID().toString());
        uploadXRayDetailDto.setFileName(requestModel.getFileName());
        uploadXRayDetailDto.setFileData(requestModel.getFileData());
        patientDetailServiceImpl.uploadXRay(uploadXRayDetailDto);
        response.setMessage("xray successfully saved");
        response.setXrayId(uploadXRayDetailDto.getXrayId());
        return ResponseEntity.status(HttpStatus.OK).
            body(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteXray(@RequestBody DownloadXRayRequestModel downloadXRayRequestModel)
    {
        return null;
    }

    /*@GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        DownloadXRayDto file = patientXRayDownloadService.downloadFile("62af038a54091e5445b857b1");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFile()));
    }*/

    @PostMapping("/getToken")
    public void getToken(@RequestHeader("Authorization") String token)
    {
        System.out.println("Token : " +token);
    }
}
