package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.data.XRayDetailDocument;
import com.iitr.gl.userdetailservice.data.XRayDetailMongoDBRepository;
import com.iitr.gl.userdetailservice.data.XRayDetailEntity;
import com.iitr.gl.userdetailservice.data.XRayDetailMySqlRepository;
import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserDashboardServiceImpl implements UserDashboardService
{
    @Autowired
    private XRayDetailMongoDBRepository xRayDetailMongoDBRepository;

    @Autowired
    private XRayDetailMySqlRepository xRayDetailMySqlRepository;

    @Override
    public DownloadFileDto downloadXray(DownloadFileDto downloadFileDto) {

        XRayDetailEntity xRayDetailEntity = xRayDetailMySqlRepository.findByXrayIdAndUserId(downloadFileDto.getXrayId(), downloadFileDto.getUserId());

        Optional<XRayDetailDocument> gridFSFile = xRayDetailMongoDBRepository.findByxrayId(downloadFileDto.getXrayId());;
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

        XRayDetailDocument xrayDetailDocument = new XRayDetailDocument();
        xrayDetailDocument.setXrayId(uploadFileDto.getXrayId());
        xrayDetailDocument.setHaspneumonia("Processing");
        xrayDetailDocument.setFilename(uploadFileDto.getFileName());
        xrayDetailDocument.setData(Base64Utils.decodeFromString(uploadFileDto.getFileData()));


        xRayDetailMongoDBRepository.save(xrayDetailDocument);
        xRayDetailMySqlRepository.save(xRayDetailEntity);

        return null;
    }

    @Override
    @Transactional
    public String deleteFile(GenericDto genericDto) {
        XRayDetailEntity xRayDetailEntity = xRayDetailMySqlRepository.findByXrayIdAndUserId(genericDto.getXrayId(), genericDto.getUserId());

        if(xRayDetailEntity !=null && xRayDetailEntity.getXrayType().equalsIgnoreCase("pneumonia")) {
            xRayDetailMongoDBRepository.deleteByXrayId(xRayDetailEntity.getXrayId());
            xRayDetailMySqlRepository.deleteByXrayId(xRayDetailEntity.getXrayId());
        }
        return "Deleted successfully";
    }
}
