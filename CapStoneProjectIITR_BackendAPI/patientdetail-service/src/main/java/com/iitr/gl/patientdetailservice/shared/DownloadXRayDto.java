package com.iitr.gl.patientdetailservice.shared;

import org.bson.types.Binary;

import java.util.UUID;

public class DownloadXRayDto
{
    private String filename;
    private byte[] file;

    private String haspneumonia;

    private String xrayId;

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
