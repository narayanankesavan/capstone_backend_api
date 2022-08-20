package com.iitr.gl.userdetailservice.shared;

public class UploadFileDto {
    private String fileName;
    private String fileData;
    private String xrayId;
    private String userId;
    private String xrayType;
    private String message;
    private String scriptId;

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getXrayId() {
        return xrayId;
    }

    public void setXrayId(String xrayId) {
        this.xrayId = xrayId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getXrayType() {
        return xrayType;
    }

    public void setXrayType(String xrayType) {
        this.xrayType = xrayType;
    }
}
