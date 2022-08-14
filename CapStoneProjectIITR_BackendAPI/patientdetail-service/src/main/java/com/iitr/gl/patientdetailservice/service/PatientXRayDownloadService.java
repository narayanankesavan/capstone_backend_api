package com.iitr.gl.patientdetailservice.service;

import com.iitr.gl.patientdetailservice.data.PatientDetailDocument;
import com.iitr.gl.patientdetailservice.data.PatientXRayMongoDBRepository;
import com.iitr.gl.patientdetailservice.shared.DownloadXRayDto;
import com.iitr.gl.patientdetailservice.ui.model.DownloadXRayModel;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class PatientXRayDownloadService
{
    @Autowired
    private PatientXRayMongoDBRepository patientXRayMongoDBRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    public DownloadXRayDto downloadFile(DownloadXRayModel downloadXRayModel) throws IOException {

        Optional<PatientDetailDocument> gridFSFile = patientXRayMongoDBRepository.findById(downloadXRayModel.getPatientId());;
        DownloadXRayDto downloadXRayDto = null;
        System.out.println(gridFSFile);
        if(gridFSFile.isPresent()) {
            System.out.println(gridFSFile.get().getPatientId());
            downloadXRayDto = new DownloadXRayDto();
            downloadXRayDto.setFile(gridFSFile.get().getData());
            downloadXRayDto.setFilename(gridFSFile.get().getFilename());
            System.out.println(downloadXRayDto.getFile());
        }
        else
            System.out.println("Empty");
        return downloadXRayDto;


        /*LoadFile loadFile = new LoadFile();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename( gridFSFile.getFilename() );

            loadFile.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );

            loadFile.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );

            loadFile.setFile( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );
        }

        return loadFile;*/
    }
}
