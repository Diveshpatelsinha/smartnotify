package com.smartnotify.security;

import com.smartnotify.feature.user.entity.User;
import com.smartnotify.feature.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("\n========== JWT FILTER ==========");
        System.out.println("Request URI : " + request.getRequestURI());
        System.out.println("Method      : " + request.getMethod());

        String token = extractToken(request);

        if (token == null) {
            System.out.println("❌ No JWT token found.");
        } else {
            System.out.println("✅ JWT token found.");
            System.out.println("Token Valid : " + jwtUtil.isTokenValid(token));
        }

        if (token != null && jwtUtil.isTokenValid(token)) {

            String email = jwtUtil.extractEmail(token);
            System.out.println("Email       : " + email);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                Optional<User> userOpt = userRepository.findByEmailWithRole(email);

                if (userOpt.isPresent()) {

                    User user = userOpt.get();

                    System.out.println("User Found  : " + user.getEmail());
                    System.out.println("Role        : " + user.getRole().getName());

                    CustomUserDetails userDetails = new CustomUserDetails(user);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("✅ Authentication set successfully.");
                    System.out.println("Authorities : " + authToken.getAuthorities());

                } else {
                    System.out.println("❌ User not found in database.");
                }

            } else {
                System.out.println("ℹ Authentication already exists.");
                System.out.println(SecurityContextHolder.getContext().getAuthentication());
            }

        } else if (token != null) {
            System.out.println("❌ Invalid or expired JWT.");
        }

        filterChain.doFilter(request, response);

        System.out.println("Response Status : " + response.getStatus());
        System.out.println("========== END JWT FILTER ==========\n");
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER_NAME);

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return header.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}