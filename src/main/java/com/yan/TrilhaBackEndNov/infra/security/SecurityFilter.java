package com.yan.TrilhaBackEndNov.infra.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.yan.TrilhaBackEndNov.model.user.User;
import com.yan.TrilhaBackEndNov.repository.userRepository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null) {
            System.out.println("Token found in request");

            try {
                String email = tokenService.validateToken(token);

                if (email != null && !email.isEmpty()) {
                    System.out.println("Valid email found in token: " + email);

                    UserDetails user = userRepository.findByEmail(email);

                        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        System.out.println("Authentication set in SecurityContext");
                    } else {
                        System.err.println("User not found in database for email: " + email);
                    }

            } catch (Exception e) {
                System.err.println("Error processing token: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No token found in request");
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            System.out.println("No Authorization header found");
            return null;
        }
        if (!authHeader.startsWith("Bearer ")) {
            System.out.println("Authorization header doesn't start with 'Bearer '");
            return null;
        }

        String token = authHeader.replace("Bearer ", "");
        System.out.println("Token recovered from header: " + token.substring(0, 10) + "...");
        return token;
    }
}