package com.iitr.gl.userdetailservice.data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "scriptDetail")
public class PythonScriptEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 6211931924885054445L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private String scriptId;
    @Column(nullable = false)
    private String userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ScriptDetailEntity{" +
                "scriptId='" + scriptId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
