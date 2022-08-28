package com.iitr.gl.userdetailservice.util;

import com.iitr.gl.userdetailservice.service.XRayService;
import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import com.iitr.gl.userdetailservice.ui.model.GenericRequestModel;
import com.iitr.gl.userdetailservice.ui.model.UploadXRayFileRequestModel;
import com.iitr.gl.userdetailservice.ui.model.UploadXRayFileResponseModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.util.UUID;

@Component
public class XRayActions {
    public ResponseEntity<ByteArrayResource> downloadXray(GenericRequestModel genericRequestModel, XRayService XRayService) {
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


    public ResponseEntity<String> viewXray(GenericRequestModel genericRequestModel, XRayService XRayService) {
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

    public ResponseEntity<UploadXRayFileResponseModel> uploadXray(UploadXRayFileRequestModel requestModel, XRayService XRayService) {
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

    public ResponseEntity<String> deleteXray(GenericRequestModel genericRequestModel, XRayService XRayService) {
        GenericDto genericDto = new GenericDto();
        genericDto.setUserId(genericRequestModel.getUserId());
        genericDto.setXrayId(genericRequestModel.getXrayId());
        HttpStatus httpStatus = XRayService.deleteXRay(genericDto);
        if (httpStatus == HttpStatus.OK)
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("For given userId, xrayId, no xray is present");
    }

    public ResponseEntity<String> deleteAllXray(GenericRequestModel requestModel, XRayService XRayService) {
        HttpStatus httpStatus = XRayService.deleteAllXRay(requestModel.getUserId());
        if (HttpStatus.OK == httpStatus)
            return ResponseEntity.status(HttpStatus.OK).
                    body("Successfully deleted");
        else if (HttpStatus.NOT_FOUND == httpStatus)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("XRays with given userId are not found");
        return null;
    }


    public ResponseEntity<String> updateXray(UploadXRayFileRequestModel requestModel, XRayService XRayService) {
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
}
