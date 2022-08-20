package com.iitr.gl.userdetailservice.service;

import com.iitr.gl.userdetailservice.data.XRayDetailDocument;
import com.iitr.gl.userdetailservice.data.XRayDetailEntity;
import com.iitr.gl.userdetailservice.data.XRayDetailMongoDBRepository;
import com.iitr.gl.userdetailservice.data.XRayDetailMySqlRepository;
import com.iitr.gl.userdetailservice.shared.DownloadFileDto;
import com.iitr.gl.userdetailservice.shared.GenericDto;
import com.iitr.gl.userdetailservice.shared.UploadFileDto;
import com.iitr.gl.userdetailservice.ui.model.ListUserFilesResponseModel;
import com.iitr.gl.userdetailservice.ui.model.XRayFileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDashboardServiceImpl implements UserDashboardService {
    @Autowired
    private XRayDetailMongoDBRepository xRayDetailMongoDBRepository;

    @Autowired
    private XRayDetailMySqlRepository xRayDetailMySqlRepository;

    @Override
    public DownloadFileDto downloadXRay(DownloadFileDto downloadFileDto) {

        XRayDetailEntity xRayDetailEntity = xRayDetailMySqlRepository.findByXrayIdAndUserId(downloadFileDto.getXrayId(), downloadFileDto.getUserId());

        Optional<XRayDetailDocument> gridFSFile = xRayDetailMongoDBRepository.findByxrayId(downloadFileDto.getXrayId());
        downloadFileDto = null;
        System.out.println(gridFSFile);
        if (gridFSFile.isPresent()) {
            System.out.println(gridFSFile.get().getPatientId());
            downloadFileDto = new DownloadFileDto();
            downloadFileDto.setFile(gridFSFile.get().getData());
            downloadFileDto.setFilename(gridFSFile.get().getFilename());
            downloadFileDto.setXrayId(gridFSFile.get().getXrayId());
            System.out.println(downloadFileDto.getFile());
        } else
            System.out.println("Empty");
        return downloadFileDto;
    }

    @Override
    public UploadFileDto uploadXRay(UploadFileDto uploadFileDto) {
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
    public String deleteXRay(GenericDto genericDto) {
        XRayDetailEntity xRayDetailEntity = xRayDetailMySqlRepository.findByXrayIdAndUserId(genericDto.getXrayId(), genericDto.getUserId());

        if (xRayDetailEntity != null && xRayDetailEntity.getXrayType().equalsIgnoreCase("pneumonia")) {
            xRayDetailMongoDBRepository.deleteByXrayId(xRayDetailEntity.getXrayId());
            xRayDetailMySqlRepository.deleteByXrayId(xRayDetailEntity.getXrayId());
        }
        return "Deleted successfully";
    }

    public String updateXRay(UploadFileDto fileDto) {
        XRayDetailEntity xRayDetailEntity = xRayDetailMySqlRepository.findByXrayIdAndUserId(fileDto.getXrayId(), fileDto.getUserId());

        System.out.println("mysql" + xRayDetailEntity.getXrayType());

        if (xRayDetailEntity != null) {
            Optional<XRayDetailDocument> document = xRayDetailMongoDBRepository.findByxrayId(fileDto.getXrayId());
            System.out.println("mongodb present " + document.isPresent());
            if (document.isPresent()) {
                XRayDetailDocument xRayDetailDocument = document.get();
                System.out.println("mongodb" + xRayDetailDocument.getHaspneumonia());
                xRayDetailDocument.setFilename(fileDto.getFileName());
                xRayDetailDocument.setData(Base64Utils.decodeFromString(fileDto.getFileData()));
                xRayDetailMongoDBRepository.save(xRayDetailDocument);
                return "Updated Successfully";
            }
        }

        return null;
    }

    public ListUserFilesResponseModel listUserFiles(String userId) {
        List<XRayDetailEntity> xRayDetailEntityList = xRayDetailMySqlRepository.findByUserId(userId);

        List<String> xrayIds = new ArrayList<>();

        xRayDetailEntityList.forEach((entity) -> xrayIds.add(entity.getXrayId()));

        List<XRayDetailDocument> xRayDetailDocuments = xRayDetailMongoDBRepository.findAllUsingXrayId(xrayIds);
        System.out.println(xRayDetailDocuments);
        ListUserFilesResponseModel listUserFilesResponseModel = new ListUserFilesResponseModel();
        List<XRayFileModel> xRayFileModelList = new ArrayList<>();
        xRayDetailDocuments.forEach(xRayDetailDocument -> {
            XRayFileModel xRayFileModel = new XRayFileModel();
            xRayFileModel.setFileData(Base64Utils.encodeToString(xRayDetailDocument.getData()));
            xRayFileModel.setFileName(xRayDetailDocument.getFilename());
            xRayFileModel.setXrayId(xRayDetailDocument.getXrayId());
            xRayFileModel.setTestResult(xRayDetailDocument.getHaspneumonia());
            xRayFileModelList.add(xRayFileModel);
        });

        listUserFilesResponseModel.setPneumoniaXray(xRayFileModelList);
        if (xRayDetailDocuments != null)
            return listUserFilesResponseModel;
        else
            return null;
    }
}
