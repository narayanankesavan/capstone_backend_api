package com.iitr.gl.userdetailservice.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PythonScriptMongoDBRepository extends MongoRepository<PythonScriptDocument, String> {
    PythonScriptDocument findByScriptId(String scriptId);
    void deleteByScriptId(String scriptId);
}
