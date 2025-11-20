package com.marketplace.auth.infrastructure.jwt;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtBuilder {

    public String buildToken(Object payload, Duration ttl, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Instant now = Instant.now();
        Instant expiresAt = now.plus(ttl);

        if (payload instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> claims = (Map<String, Object>) payload;

            JWTCreator.Builder jwtBuilder = JWT.create()
                    .withIssuedAt(Date.from(now))
                    .withExpiresAt(Date.from(expiresAt));

            if (claims.containsKey("sub")) {
                jwtBuilder = jwtBuilder.withSubject((String) claims.get("sub"));
            }
            if (claims.containsKey("iss")) {
                jwtBuilder = jwtBuilder.withIssuer((String) claims.get("iss"));
            }

            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                String key = entry.getKey();
                if (!"sub".equals(key) && !"iss".equals(key)) {
                    jwtBuilder = jwtBuilder.withClaim(key, entry.getValue().toString());
                }
            }

            return jwtBuilder.sign(algorithm);
        } else if (payload instanceof String) {
            return JWT.create()
                    .withSubject((String) payload)
                    .withIssuedAt(Date.from(now))
                    .withExpiresAt(Date.from(expiresAt))
                    .sign(algorithm);
        } else {
            return JWT.create()
                    .withSubject(payload.toString())
                    .withIssuedAt(Date.from(now))
                    .withExpiresAt(Date.from(expiresAt))
                    .sign(algorithm);
        }
    }
}
