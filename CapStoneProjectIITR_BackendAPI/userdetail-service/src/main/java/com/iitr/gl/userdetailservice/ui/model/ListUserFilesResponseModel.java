package com.iitr.gl.userdetailservice.ui.model;

import java.util.List;

public class ListUserFilesResponseModel {

    public List<XRayFileModel> pneumoniaXray;

    public List<ScriptFileModel> scripts;

    public List<XRayFileModel> getPneumoniaXray() {
        return pneumoniaXray;
    }

    public void setPneumoniaXray(List<XRayFileModel> pneumoniaXray) {
        this.pneumoniaXray = pneumoniaXray;
    }

    public List<ScriptFileModel> getScripts() {
        return scripts;
    }

    public void setScripts(List<ScriptFileModel> scripts) {
        this.scripts = scripts;
    }
}
