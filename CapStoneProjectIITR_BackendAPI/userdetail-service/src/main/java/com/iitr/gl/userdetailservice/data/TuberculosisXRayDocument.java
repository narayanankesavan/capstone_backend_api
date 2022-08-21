package com.iitr.gl.userdetailservice.data;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "tuberculosis_detection")
public class TuberculosisXRayDocument {
    @Id
    private String patientId;
    private byte[] data;
    private String filename;

    private String hastb;
    @Field("xrayid")
    private String xrayId;

    public String getHastb() {
        return hastb;
    }

    public void setHastb(String hastb) {
        this.hastb = hastb;
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
