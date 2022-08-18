package com.iitr.gl.patientdetailservice.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientXRayMongoDBRepository extends MongoRepository<PatientDetailDocument, String> {
    Optional<PatientDetailDocument>  findByxrayId(String xrayId);
}
