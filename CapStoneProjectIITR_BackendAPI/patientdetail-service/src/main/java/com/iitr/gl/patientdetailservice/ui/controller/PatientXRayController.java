package com.iitr.gl.patientdetailservice.ui.controller;

import com.iitr.gl.patientdetailservice.data.PatientXRayMongoDBRepository;
import com.iitr.gl.patientdetailservice.service.LoginServiceClient;
import com.iitr.gl.patientdetailservice.ui.model.PatientXRayDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient_detail/")
@RequiredArgsConstructor
public class PatientXRayController {
    private final PatientXRayMongoDBRepository patientXRayMongoDBRepository;
    @Autowired
    Environment environment;
    @Autowired
    LoginServiceClient loginServiceClient;

    @PostMapping("/addDetail")
    public void addXRayDetail(@RequestBody PatientXRayDetails patientXRayDetails) {
        patientXRayMongoDBRepository.save(patientXRayDetails);
    }

    @GetMapping("/{id}")
    public Object getPatientDetail(@PathVariable String id) {
        return patientXRayMongoDBRepository.findById(id);
    }

    @GetMapping("/test")
    public String test() {
        return environment.getProperty("value");
    }

    @GetMapping("/testRemoteMicroService")
    public String testRemoteMicroService() {
        return loginServiceClient.testRemote();
    }
}
