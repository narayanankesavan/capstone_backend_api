package com.iitr.gl.userdetailservice.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientXRayMongoDBRepository extends MongoRepository<PatientXrayDetailDocument, String> {
    Optional<PatientXrayDetailDocument>  findByxrayId(String xrayId);
}
