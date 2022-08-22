package com.iitr.gl.userdetailservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface PythonScriptMySqlRepository extends JpaRepository<PythonScriptEntity, Long> {
    PythonScriptEntity findByScriptIdAndUserId(String scriptId, String userId);
    @Transactional
    void deleteByScriptId(String scriptId);
}
