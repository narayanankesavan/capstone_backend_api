package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import com.iitr.gl.userdetailservice.ui.model.ListUserFilesResponseModel;
import org.springframework.http.HttpStatus;

public interface XRayService {
    DownloadFileDto downloadXRay(DownloadFileDto downloadFileDto);

    void uploadXRay(UploadFileDto uploadFileDto);

    HttpStatus deleteXRay(GenericDto genericDto);

    HttpStatus updateXRay(UploadFileDto fileDto);

    ListUserFilesResponseModel listUserFiles(String userId);

    HttpStatus deleteAllXRay(String userId);
}
