package com.example.BaiTest.filters;

import com.example.BaiTest.components.JwtTokenUtils;
import com.example.BaiTest.exceptions.DataNotFoundException;
import com.example.BaiTest.model.RefreshTokens;
import com.example.BaiTest.model.User;
import com.example.BaiTest.repository.RefreshTokensRepo;
import com.example.BaiTest.repository.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter{
    @Value("${api.prefix}")
    private String apiPrefix;
    @Value("${jwt.expiration}")
    private int expiration;

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtil;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RefreshTokensRepo refreshTokensRepo;
    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response); //enable bypass
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            final String token = authHeader.substring(7);
            final String email = jwtTokenUtil.extractEmail(token);

            User user = userRepo.findByEmail(email).orElse(null);
            if (email != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(email);
                if(jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token has expired");
                    return;
                }
            }
            filterChain.doFilter(request, response); //enable bypass
        }catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
    private boolean isBypassToken(@NonNull  HttpServletRequest request) {

        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/roles", apiPrefix), "GET"),
                Pair.of(String.format("%s/products", apiPrefix), "GET"),
                Pair.of(String.format("%s/categories", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/courseType/top3CourseType", apiPrefix), "GET")
        );
        for(Pair<String, String> bypassToken: bypassTokens) {
            if (request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())) {
                return true;
            }
        }
        return false;
    }
}
