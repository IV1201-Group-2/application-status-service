package com.example.applicationstatusservice.service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * JwtAuthService is a service class meant to handle the logic
 * for authentication and authorization of JWT tokens.
 */
@Service
@SuppressFBWarnings("DM_DEFAULT_ENCODING")
public class JwtAuthService {

    /**
     * Logger to log events passing the JWT authentication and authorization logic.
     */
    private static final Logger logger = LogManager.getLogger(JwtAuthService.class);

    /**
     * Config variable JWT secret from Heroku.
     */
    @Value("${JWT_SECRET:FKi2FTPuzT6XzXZnDjR4Z2X5Uu2+C3yNq3BgtHJvd4g=}")
    private String JWT_SECRET;

    /**
     * Method for  authentication and authorization of JWT tokens.
     *
     * @param header contains the encoded JWT token.
     * @return Response strings: AUTHORIZED for when the decoding and validation are successful and
     * UNAUTHORIZED for when decoding failed or role value was not 1.
     */
    public String jwtAuth(String header) {
        String jwtToken = header.replace("Bearer ", "");
        logger.debug("Currently processed JWT token: {} ", jwtToken);
        SecretKeySpec secKey = new SecretKeySpec(JWT_SECRET.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        try {
            Jws<Claims> parseJwtClaims = Jwts.parserBuilder().setSigningKey(secKey).build().parseClaimsJws(jwtToken);
            Claims claims = parseJwtClaims.getBody();
            Integer roleValue = claims.get("role", Integer.class);
            if (roleValue != null && roleValue.equals(1)) {
                System.out.println("role 1");
                logger.info("Authorized user");
                return "AUTHORIZED";
            } else {
                logger.info("Unauthorized user");
                System.out.println("role isnt 1");
                return "UNAUTHORIZED";
            }
        } catch (Exception e) {
            System.out.println("deep shit");
            return "UNAUTHORIZED";
        }
    }

    /**
     * Creates JWT tokens to use during integration testing.
     *
     * @return JWT tokens encoded using HS256 algorithm.
     */
    public String jwtCreateTestTokens() {
        SecretKeySpec keyTest = new SecretKeySpec(JWT_SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder().claim("usage", "login").claim("id", 5).claim("username", "MaxwellBailey").claim("role", 1).signWith(keyTest).compact();
    }
}
