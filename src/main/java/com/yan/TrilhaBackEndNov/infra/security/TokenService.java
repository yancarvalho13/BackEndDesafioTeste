package com.yan.TrilhaBackEndNov.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.yan.TrilhaBackEndNov.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            if (user == null) {
                throw new IllegalArgumentException("User cannot be null");
            }
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                throw new IllegalArgumentException("User email cannot be null or empty");
            }

            System.out.println("Generating token for user: " + user.getEmail());

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

            System.out.println("Token generated successfully: " + token.substring(0, 10) + "...");
            return token;

        } catch (JWTCreationException exception) {
            System.err.println("Error generating token: " + exception.getMessage());
            throw new RuntimeException("Error while generating token: ", exception);
        }
    }

    public String validateToken(String token) {
        try {
            if (token == null || token.isEmpty()) {
                System.err.println("Token is null or empty");
                return "";
            }

            System.out.println("Validating token: " + token.substring(0, 10) + "...");

            Algorithm algorithm = Algorithm.HMAC256(secret);
            var decodedJWT = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);

            String email = decodedJWT.getSubject();

            if (email == null || email.isEmpty()) {
                System.err.println("No email found in token");
                return "";
            }

            System.out.println("Token validated successfully. Email: " + email);
            return email;

        } catch (JWTVerificationException exception) {
            System.err.println("Token validation error: " + exception.getMessage());
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
