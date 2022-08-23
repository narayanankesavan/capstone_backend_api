package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import com.iitr.gl.userdetailservice.ui.model.ScriptFileModel;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface PythonScriptService {
    void uploadPythonScript(UploadFileDto fileDto);

    HttpStatus updatePythonScript(UploadFileDto fileDto);

    HttpStatus deletePythonScript(GenericDto dto);

    DownloadFileDto downloadPythonScript(DownloadFileDto dto);

    List<ScriptFileModel> listUserFiles(String userId);

    HttpStatus deleteAllPythonScript(String userId);
}
