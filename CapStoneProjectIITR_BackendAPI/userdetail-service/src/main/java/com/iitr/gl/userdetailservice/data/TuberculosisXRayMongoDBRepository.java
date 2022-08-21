package com.iitr.gl.userdetailservice.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TuberculosisXRayMongoDBRepository extends MongoRepository<TuberculosisXRayDocument, String> {
    TuberculosisXRayDocument findByxrayId(String xrayId);

    void deleteByXrayId(String xrayId);

    @Query(value = "{'xrayid' : {$in: ?0} }")
    List<TuberculosisXRayDocument> findAllUsingXrayId(List<String> xrayids);

}
