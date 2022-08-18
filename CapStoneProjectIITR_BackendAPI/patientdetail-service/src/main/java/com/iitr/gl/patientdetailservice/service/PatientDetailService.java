package com.iitr.gl.patientdetailservice.service;

import com.iitr.gl.patientdetailservice.shared.DownloadXRayDto;
import com.iitr.gl.patientdetailservice.shared.UploadXRayDetailDto;
import com.iitr.gl.patientdetailservice.ui.model.DownloadXRayRequestModel;
import com.iitr.gl.patientdetailservice.ui.model.UploadXRayRequestModel;

public interface PatientDetailService {
    public DownloadXRayDto downloadXray(DownloadXRayRequestModel downloadXRayRequestModel);
    public UploadXRayDetailDto uploadXRay(UploadXRayDetailDto uploadXRayDetailDto);
}
