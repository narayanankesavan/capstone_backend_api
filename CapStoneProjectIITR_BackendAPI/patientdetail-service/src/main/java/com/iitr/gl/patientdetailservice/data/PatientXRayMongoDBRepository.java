package com.iitr.gl.patientdetailservice.data;

import com.iitr.gl.patientdetailservice.ui.model.PatientXRayDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientXRayMongoDBRepository extends MongoRepository<PatientXRayDetails, String> {
}
