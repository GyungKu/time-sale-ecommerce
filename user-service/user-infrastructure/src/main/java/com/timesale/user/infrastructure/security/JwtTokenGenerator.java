package com.timesale.user.infrastructure.security;

import com.timesale.user.application.port.TokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenGenerator implements TokenGenerator {

    private final SecretKey secretKey;
    private final Long accessTokenValidityInMilliseconds;

    public JwtTokenGenerator(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.access-token-validity-in-seconds}") Long validityInSeconds) {

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = validityInSeconds * 1000;
    }

    @Override
    public String generateAccessToken(Long userId, String email) {
        Date date = new Date();
        Date validity = new Date(date.getTime() + accessTokenValidityInMilliseconds);

        return Jwts.builder()
            .subject(userId.toString())
            .claim("email", email)
            .issuedAt(date)
            .expiration(validity)
            .signWith(secretKey)
            .compact();
    }
}
