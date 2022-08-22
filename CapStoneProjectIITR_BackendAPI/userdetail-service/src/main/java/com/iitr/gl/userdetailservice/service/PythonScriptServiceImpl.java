package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.data.PythonScriptDocument;
import com.iitr.gl.userdetailservice.data.PythonScriptEntity;
import com.iitr.gl.userdetailservice.data.PythonScriptMongoDBRepository;
import com.iitr.gl.userdetailservice.data.PythonScriptMySqlRepository;
import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    @Override
    public HttpStatus updatePythonScript(UploadFileDto fileDto) {

        PythonScriptEntity pythonScriptEntity = pythonScriptMySqlRepository.findByScriptIdAndUserId(fileDto.getScriptId(), fileDto.getUserId());
        if(pythonScriptEntity != null) {
            PythonScriptDocument pythonScriptDocument = pythonScriptMongoDBRepository.findByScriptId(fileDto.getScriptId());
            pythonScriptDocument.setFileName(fileDto.getFileName());
            pythonScriptDocument.setData(Base64Utils.decodeFromString(fileDto.getFileData()));
            pythonScriptMongoDBRepository.save(pythonScriptDocument);
            return HttpStatus.OK;
        }

        return HttpStatus.NOT_FOUND;
    }

    @Override
    public HttpStatus deletePythonScript(GenericDto dto)
    {
        PythonScriptEntity pythonScriptEntity = pythonScriptMySqlRepository.findByScriptIdAndUserId(dto.getScriptId(), dto.getUserId());
        if(pythonScriptEntity != null) {
            pythonScriptMongoDBRepository.deleteByScriptId(dto.getScriptId());
            pythonScriptMySqlRepository.deleteByScriptId(dto.getScriptId());
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public DownloadFileDto downloadPythonScript(DownloadFileDto dto)
    {
        PythonScriptEntity pythonScriptEntity = pythonScriptMySqlRepository.findByScriptIdAndUserId(dto.getScriptId(), dto.getUserId());
        DownloadFileDto downloadFileDto = new DownloadFileDto();
        if(pythonScriptEntity != null)
        {
            PythonScriptDocument pythonScriptDocument = pythonScriptMongoDBRepository.findByScriptId(dto.getScriptId());
            downloadFileDto.setFilename(pythonScriptDocument.getFileName());
            downloadFileDto.setFile(pythonScriptDocument.getData());
            return downloadFileDto;
        }else {
            downloadFileDto.setErrorMessage("For given userId, scriptId, no python script is found");
            downloadFileDto.setFile(null);
            return downloadFileDto;
        }
    }
}
