package com.iitr.gl.loginservice.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySqlLoginRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
}
