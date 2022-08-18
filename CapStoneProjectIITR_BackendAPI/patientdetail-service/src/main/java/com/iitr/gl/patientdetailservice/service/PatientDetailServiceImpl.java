package com.iitr.gl.patientdetailservice.service;

import com.iitr.gl.patientdetailservice.data.PatientDetailDocument;
import com.iitr.gl.patientdetailservice.data.PatientXRayMongoDBRepository;
import com.iitr.gl.patientdetailservice.data.XRayDetailEntity;
import com.iitr.gl.patientdetailservice.data.XRayDetailRepository;
import com.iitr.gl.patientdetailservice.shared.DownloadXRayDto;
import com.iitr.gl.patientdetailservice.shared.UploadXRayDetailDto;
import com.iitr.gl.patientdetailservice.ui.model.DownloadXRayRequestModel;
import com.iitr.gl.patientdetailservice.ui.model.UploadXRayRequestModel;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientDetailServiceImpl implements PatientDetailService
{
    @Autowired
    private PatientXRayMongoDBRepository patientXRayMongoDBRepository;

    @Autowired
    private XRayDetailRepository xRayDetailRepository;

    @Override
    public DownloadXRayDto downloadXray(DownloadXRayRequestModel downloadXRayRequestModel) {

        Optional<PatientDetailDocument> gridFSFile = patientXRayMongoDBRepository.findByxrayId(downloadXRayRequestModel.getXrayId());;
        DownloadXRayDto downloadXRayDto = null;
        System.out.println(gridFSFile);
        if(gridFSFile.isPresent()) {
            System.out.println(gridFSFile.get().getPatientId());
            downloadXRayDto = new DownloadXRayDto();
            downloadXRayDto.setFile(gridFSFile.get().getData());
            downloadXRayDto.setFilename(gridFSFile.get().getFilename());
            downloadXRayDto.setXrayId(gridFSFile.get().getXrayId());
            System.out.println(downloadXRayDto.getFile());
        }
        else
            System.out.println("Empty");
        return downloadXRayDto;
    }

    @Override
    public UploadXRayDetailDto uploadXRay(UploadXRayDetailDto uploadXRayDetailDto)
    {
        XRayDetailEntity xRayDetailEntity = new XRayDetailEntity();
        xRayDetailEntity.setUserId(uploadXRayDetailDto.getUserId());
        xRayDetailEntity.setXrayType(uploadXRayDetailDto.getXrayType());
        xRayDetailEntity.setXrayId(uploadXRayDetailDto.getXrayId());

        PatientDetailDocument patientDetailDocument = new PatientDetailDocument();
        patientDetailDocument.setXrayId(uploadXRayDetailDto.getXrayId());
        patientDetailDocument.setHaspneumonia("Processing");
        patientDetailDocument.setFilename(uploadXRayDetailDto.getFileName());
        patientDetailDocument.setData(Base64Utils.decodeFromString(uploadXRayDetailDto.getFileData()));


        patientXRayMongoDBRepository.save(patientDetailDocument);
        xRayDetailRepository.save(xRayDetailEntity);

        return null;
    }
}
