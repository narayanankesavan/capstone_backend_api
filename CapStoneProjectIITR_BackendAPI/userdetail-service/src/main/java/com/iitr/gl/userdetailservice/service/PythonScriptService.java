package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import org.springframework.http.HttpStatus;

public interface PythonScriptService {
    void uploadPythonScript(UploadFileDto fileDto);
    HttpStatus updatePythonScript(UploadFileDto fileDto);
    HttpStatus deletePythonScript(GenericDto dto);
    DownloadFileDto downloadPythonScript(DownloadFileDto dto);
}
