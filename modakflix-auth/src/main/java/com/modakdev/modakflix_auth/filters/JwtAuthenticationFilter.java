package com.modakdev.modakflix_auth.filters;

import com.modakdev.modakflix_auth.api.service.impl.ModakFlixServerAuthServiceImpl;
import com.modakdev.models.entities.ModakFlixServerUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ModakFlixServerAuthServiceImpl modakFlixServerAuthService;

    public JwtAuthenticationFilter(ModakFlixServerAuthServiceImpl modakFlixServerAuthService) {
        this.modakFlixServerAuthService = modakFlixServerAuthService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Optional<ModakFlixServerUser> modakFlixServerUserOptional = Optional.ofNullable(modakFlixServerAuthService.validateToken(token));
                modakFlixServerUserOptional.ifPresent(modakFlixServerUser -> {
                    String username = modakFlixServerUser.getUsername();
                    String role = modakFlixServerUser.getRole();

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                            );

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });

            } catch (Exception e) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                // Write response body
                response.getWriter().write("{\"error\": \"Invalid JWT Token\", \"message\": \"" + e.getMessage() + "\"}");
                response.getWriter().flush();
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
