package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;

public interface UserDashboardService {
    public DownloadFileDto downloadXRay(DownloadFileDto downloadFileDto);

    public UploadFileDto uploadXRay(UploadFileDto uploadFileDto);

    public String deleteXRay(GenericDto genericDto);

    String updateXRay(UploadFileDto fileDto);
}
