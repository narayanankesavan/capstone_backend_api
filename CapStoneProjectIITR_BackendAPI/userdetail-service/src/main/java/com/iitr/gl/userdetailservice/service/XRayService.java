package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import com.iitr.gl.userdetailservice.ui.model.ListUserFilesResponseModel;
import org.springframework.http.HttpStatus;

public interface XRayService {
    public DownloadFileDto downloadXRay(DownloadFileDto downloadFileDto);

    public void uploadXRay(UploadFileDto uploadFileDto);

    public HttpStatus deleteXRay(GenericDto genericDto);

    public HttpStatus updateXRay(UploadFileDto fileDto);

    public ListUserFilesResponseModel listUserFiles(String userId);
}
