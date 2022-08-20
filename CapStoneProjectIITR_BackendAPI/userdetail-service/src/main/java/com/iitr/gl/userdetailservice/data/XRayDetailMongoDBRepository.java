package com.iitr.gl.userdetailservice.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface XRayDetailMongoDBRepository extends MongoRepository<XRayDetailDocument, String> {
    Optional<XRayDetailDocument> findByxrayId(String xrayId);

    void deleteByXrayId(String xrayId);

    @Query(value = "{'xrayid' : {$in: ?0} }")
    List<XRayDetailDocument> findAllUsingXrayId(List<String> xrayids);

}
