package com.example.applicationstatusservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;

/**
 * JwtAuthService is a service class meant to handle the logic
 * for authentication and authorization of JWT tokens.
 */
@Service
public class JwtAuthService {

    /**
     * Config variable JWT secret from Heroku.
     */
    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    /**
     * Method for  authentication and authorization of JWT tokens.
     * @param header contains the encoded JWT token.
     * @return Response strings: AUTHORIZED for when the decoding and validation are successful and
     * UNAUTHORIZED for when decoding failed or role value was not 1.
     */
    public String jwtAuth(String header) {
        String jwtToken = header.replace("Bearer ", "");
        SecretKeySpec secKey = new SecretKeySpec(JWT_SECRET.getBytes(),
                SignatureAlgorithm.HS256.getJcaName());
        try {
            Jws<Claims> parseJwtClaims = Jwts.parserBuilder()
                    .setSigningKey(secKey)
                    .build().parseClaimsJws(jwtToken);
            Claims claims = parseJwtClaims.getBody();
            Integer roleValue = claims.get("role", Integer.class);
            if (roleValue != null && roleValue.equals(1)) {
                return "AUTHORIZED";
            } else {
                return "UNAUTHORIZED";
            }
        } catch (Exception e) {
            return "UNAUTHORIZED";
        }
    }
}
