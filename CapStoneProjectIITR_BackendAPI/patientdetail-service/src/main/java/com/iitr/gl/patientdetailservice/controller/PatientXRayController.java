package com.iitr.gl.patientdetailservice.controller;

import com.iitr.gl.patientdetailservice.model.PatientXRayDetails;
import com.iitr.gl.patientdetailservice.repository.PatientXRayMongoDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient_detail/")
@RequiredArgsConstructor
@RefreshScope
public class PatientXRayController {
    private final PatientXRayMongoDBRepository patientXRayMongoDBRepository;
    @Value("${testvalue}")
    private String value;

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
        return value;
    }
}
