package com.iitr.gl.userdetailservice.util;

import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;


public class GetJwtSubject {

    public String getSubject(String jwt, Environment environment) {
        String subject = Jwts.parser().
                setSigningKey(environment.getProperty("token.secret")).parseClaimsJws(jwt).
                getBody().getSubject();
        return subject;
    }
}
