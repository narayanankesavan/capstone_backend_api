package com.iitr.gl.userdetailservice.data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "xrayDetail")
public class XRayDetailEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 4526765750081687792L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String xrayId;
    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String xrayType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "XRayDetailEntity{" +
                "xrayId='" + xrayId + '\'' +
                ", userId='" + userId + '\'' +
                ", xrayType='" + xrayType + '\'' +
                '}';
    }
}
