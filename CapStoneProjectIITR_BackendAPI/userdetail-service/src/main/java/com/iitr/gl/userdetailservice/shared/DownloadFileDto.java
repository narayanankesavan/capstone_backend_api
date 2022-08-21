package com.iitr.gl.userdetailservice.shared;

public class DownloadFileDto {
    private String filename;
    private byte[] file;
    private String haspneumonia;
    private String hastuberculosis;
    private String xrayId;
    private String userId;
    private String scriptId;
    private String errorMessage;

    public String getHastuberculosis() {
        return hastuberculosis;
    }

    public void setHastuberculosis(String hastuberculosis) {
        this.hastuberculosis = hastuberculosis;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHaspneumonia() {
        return haspneumonia;
    }

    public void setHaspneumonia(String haspneumonia) {
        this.haspneumonia = haspneumonia;
    }

    public String getXrayId() {
        return xrayId;
    }

    public void setXrayId(String xrayId) {
        this.xrayId = xrayId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
