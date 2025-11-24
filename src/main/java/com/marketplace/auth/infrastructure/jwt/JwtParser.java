package com.marketplace.auth.infrastructure.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtParser {
    public Map<String, Object> parseToken(String token, String secret) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(10)
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            // Convert claims Map<String, Claim> to Map<String, Object>
            Map<String, Object> claimsMap = new HashMap<>();
            Map<String, Claim> claims = jwt.getClaims();
            for (Map.Entry<String, Claim> entry : claims.entrySet()) {
                Claim claim = entry.getValue();
                if (claim != null && !claim.isNull()) {
                    claimsMap.put(entry.getKey(), claim.asString());
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("subject", jwt.getSubject());
            result.put("issuedAt", jwt.getIssuedAt());
            result.put("expiresAt", jwt.getExpiresAt());
            result.put("claims", claimsMap);
            return result;
        } catch (JWTVerificationException e) {
            log.error("Error parsing token", e);
            throw new JWTVerificationException("Invalid token", e);
        }
    }

    public String getSubject(String token, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);
        return decodedJWT.getSubject();
    }
}
