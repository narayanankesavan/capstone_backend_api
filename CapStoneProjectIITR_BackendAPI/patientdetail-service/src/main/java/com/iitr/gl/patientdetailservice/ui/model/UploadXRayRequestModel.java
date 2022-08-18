package com.iitr.gl.patientdetailservice.ui.model;

public class UploadXRayRequestModel
{
    private String fileData;
    private String xrayType;

    private String fileName;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getXrayType() {
        return xrayType;
    }

    public void setXrayType(String xrayType) {
        this.xrayType = xrayType;
    }
}
