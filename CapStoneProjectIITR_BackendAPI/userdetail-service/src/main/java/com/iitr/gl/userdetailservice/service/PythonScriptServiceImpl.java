package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.data.PythonScriptDocument;
import com.iitr.gl.userdetailservice.data.PythonScriptEntity;
import com.iitr.gl.userdetailservice.data.PythonScriptMongoDBRepository;
import com.iitr.gl.userdetailservice.data.PythonScriptMySqlRepository;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.UUID;

@Service
public class PythonScriptServiceImpl implements PythonScriptService {

    @Autowired
    PythonScriptMySqlRepository pythonScriptMySqlRepository;

    @Autowired
    PythonScriptMongoDBRepository pythonScriptMongoDBRepository;

    @Override
    public void uploadPythonScript(UploadFileDto fileDto) {
        PythonScriptDocument pythonScriptDocument = new PythonScriptDocument();
        pythonScriptDocument.setFileName(fileDto.getFileName());
        pythonScriptDocument.setScriptId(UUID.randomUUID().toString());
        pythonScriptDocument.setData(Base64Utils.decodeFromString(fileDto.getFileData()));
        pythonScriptMongoDBRepository.save(pythonScriptDocument);

        PythonScriptEntity pythonScriptEntity = new PythonScriptEntity();
        pythonScriptEntity.setScriptId(fileDto.getScriptId());
        pythonScriptEntity.setUserId(fileDto.getUserId());
        pythonScriptMySqlRepository.save(pythonScriptEntity);
    }
}
