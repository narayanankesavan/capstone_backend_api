package com.iitr.gl.userdetailservice.data;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "pneumonia_detection")
public class PneumoniaXRayDocument {
    @Id
    private String patientId;
    private byte[] data;
    private String filename;

    private String haspneumonia;

    @Field("xrayid")
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
