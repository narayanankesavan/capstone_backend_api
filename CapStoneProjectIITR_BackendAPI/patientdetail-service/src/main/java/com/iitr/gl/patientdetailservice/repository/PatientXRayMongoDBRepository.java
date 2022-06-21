package com.iitr.gl.patientdetailservice.repository;

import com.iitr.gl.patientdetailservice.model.PatientXRayDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientXRayMongoDBRepository extends MongoRepository<PatientXRayDetails, String> {
}
