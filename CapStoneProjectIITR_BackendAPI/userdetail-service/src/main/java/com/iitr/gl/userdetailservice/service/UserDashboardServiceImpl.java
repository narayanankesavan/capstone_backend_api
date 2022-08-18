package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.data.PatientXrayDetailDocument;
import com.iitr.gl.userdetailservice.data.PatientXRayMongoDBRepository;
import com.iitr.gl.userdetailservice.data.XRayDetailEntity;
import com.iitr.gl.userdetailservice.data.XRayDetailMySqlRepository;
import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Optional;

@Service
public class UserDashboardServiceImpl implements UserDashboardService
{
    @Autowired
    private PatientXRayMongoDBRepository patientXRayMongoDBRepository;

    @Autowired
    private XRayDetailMySqlRepository xRayDetailMySqlRepository;

    @Override
    public DownloadFileDto downloadXray(DownloadFileDto downloadFileDto) {

        XRayDetailEntity xRayDetailEntity = xRayDetailMySqlRepository.findByXrayIdAndUserId(downloadFileDto.getXrayId(), downloadFileDto.getUserId());

        Optional<PatientXrayDetailDocument> gridFSFile = patientXRayMongoDBRepository.findByxrayId(downloadFileDto.getXrayId());;
        downloadFileDto = null;
        System.out.println(gridFSFile);
        if(gridFSFile.isPresent()) {
            System.out.println(gridFSFile.get().getPatientId());
            downloadFileDto = new DownloadFileDto();
            downloadFileDto.setFile(gridFSFile.get().getData());
            downloadFileDto.setFilename(gridFSFile.get().getFilename());
            downloadFileDto.setXrayId(gridFSFile.get().getXrayId());
            System.out.println(downloadFileDto.getFile());
        }
        else
            System.out.println("Empty");
        return downloadFileDto;
    }

    @Override
    public UploadFileDto uploadXRay(UploadFileDto uploadFileDto)
    {
        XRayDetailEntity xRayDetailEntity = new XRayDetailEntity();
        xRayDetailEntity.setUserId(uploadFileDto.getUserId());
        xRayDetailEntity.setXrayType(uploadFileDto.getXrayType());
        xRayDetailEntity.setXrayId(uploadFileDto.getXrayId());

        PatientXrayDetailDocument patientXrayDetailDocument = new PatientXrayDetailDocument();
        patientXrayDetailDocument.setXrayId(uploadFileDto.getXrayId());
        patientXrayDetailDocument.setHaspneumonia("Processing");
        patientXrayDetailDocument.setFilename(uploadFileDto.getFileName());
        patientXrayDetailDocument.setData(Base64Utils.decodeFromString(uploadFileDto.getFileData()));


        patientXRayMongoDBRepository.save(patientXrayDetailDocument);
        xRayDetailMySqlRepository.save(xRayDetailEntity);

        return null;
    }
}
