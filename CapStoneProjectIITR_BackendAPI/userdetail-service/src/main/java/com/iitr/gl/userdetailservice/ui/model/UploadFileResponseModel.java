package com.iitr.gl.userdetailservice.ui.model;

public class UploadFileResponseModel {

    private String message;
    private String xrayId;
    private String scriptId;

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
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
}
