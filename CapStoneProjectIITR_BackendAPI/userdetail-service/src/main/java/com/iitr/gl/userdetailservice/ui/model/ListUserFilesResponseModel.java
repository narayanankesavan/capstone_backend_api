package com.iitr.gl.userdetailservice.ui.model;

import java.util.List;

public class ListUserFilesResponseModel {

    public List<XRayFileModel> pneumoniaXray;

    public List<XRayFileModel> tuberculosisXray;

    public List<ScriptFileModel> scripts;

    public List<XRayFileModel> getPneumoniaXray() {
        return pneumoniaXray;
    }

    public void setPneumoniaXray(List<XRayFileModel> pneumoniaXray) {
        this.pneumoniaXray = pneumoniaXray;
    }

    public List<XRayFileModel> getTuberculosisXray() {
        return tuberculosisXray;
    }

    public void setTuberculosisXray(List<XRayFileModel> tuberculosisXray) {
        this.tuberculosisXray = tuberculosisXray;
    }

    public List<ScriptFileModel> getScripts() {
        return scripts;
    }

    public void setScripts(List<ScriptFileModel> scripts) {
        this.scripts = scripts;
    }
}
