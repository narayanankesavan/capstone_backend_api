package com.iitr.gl.userdetailservice.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PythonScriptMongoDBRepository extends MongoRepository<PythonScriptDocument, String> {
    PythonScriptDocument findByScriptId(String scriptId);

    void deleteByScriptId(String scriptId);

    @Query(value = "{'scriptId' : {$in: ?0} }")
    List<PythonScriptDocument> findAllUsingScriptId(List<String> scriptId);

    @Query(value = "{'scriptId' : {$in: ?0} }", delete = true)
    void deleteAllUsingScriptId(List<String> scriptId);
}
