package com.iitr.gl.loginservice.repository;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.iitr.gl.loginservice.model.LoginDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MySqlLoginRepository extends JpaRepository<LoginDetail, Integer> {

    @Query("Select u from LoginDetail u where u.user_email=?1")
    LoginDetail findUserByEmail(String user_email);
}
