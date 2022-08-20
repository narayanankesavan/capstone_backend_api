package com.iitr.gl.userdetailservice.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.adminUser=true where u.userId=?1")
    int upgradeUserToAdmin(String userId);
}
