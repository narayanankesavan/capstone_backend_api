package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;

public interface UserDashboardService {
    public DownloadFileDto downloadXray(DownloadFileDto downloadFileDto);
    public UploadFileDto uploadXRay(UploadFileDto uploadFileDto);
}
