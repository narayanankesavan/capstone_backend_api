package com.iitr.gl.userdetailservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PythonScriptMySqlRepository extends JpaRepository<PythonScriptEntity, Long> {
    PythonScriptEntity findByScriptIdAndUserId(String scriptId, String userId);

    List<PythonScriptEntity> findByUserId(String userId);

    void deleteByScriptId(String scriptId);

    void deleteByUserId(String userId);
}
