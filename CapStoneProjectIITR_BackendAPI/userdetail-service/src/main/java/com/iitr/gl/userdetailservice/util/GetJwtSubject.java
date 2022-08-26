package com.iitr.gl.userdetailservice.util;

import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;


public class GetJwtSubject {

    public boolean isAuthorized(String jwt, Environment environment, String userId, boolean isAdmin) {
        String subject = Jwts.parser().
                setSigningKey(environment.getProperty("token.secret")).parseClaimsJws(jwt).
                getBody().getSubject();
        String[] userDetail = subject.split(":::");
        if (isAdmin) {
            if (userDetail[1].equals("true"))
                return true;
            else return false;
        } else {
            if (!userDetail[1].equals("true") && userDetail[0].equals(userId))
                return true;
            else return false;
        }
    }
}
