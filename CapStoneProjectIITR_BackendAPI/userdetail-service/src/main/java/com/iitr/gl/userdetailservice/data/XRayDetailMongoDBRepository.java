package com.iitr.gl.userdetailservice.data;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface XRayDetailMongoDBRepository extends MongoRepository<XRayDetailDocument, String> {
    Optional<XRayDetailDocument>  findByxrayId(String xrayId);
    void deleteByXrayId(String xrayId);
}
