package com.iitr.gl.userdetailservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface XRayDetailMySqlRepository extends JpaRepository<XRayDetailEntity, Long> {
        XRayDetailEntity findByXrayIdAndUserId(String xrayId, String userId);
}
