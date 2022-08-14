package com.iitr.gl.patientdetailservice.shared;

public class DownloadXRayDto
{
    private String filename;
    private byte[] file;

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
