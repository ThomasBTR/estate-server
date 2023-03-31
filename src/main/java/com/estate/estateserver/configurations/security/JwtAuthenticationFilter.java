package com.estate.estateserver.configurations.security;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request     current HTTP request
     * @param response    current HTTP response
     * @param filterChain chain of filters
     */
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        {
            try {
                filterOutOrVerifyToken(request);
                filterChain.doFilter(request, response);
            } catch (AccessDeniedException e) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            }
        }
    }

    private void filterOutOrVerifyToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String requestURI = request.getRequestURI();
        // Do not filter login and register requests as well as swagger-ui and api-docs requests
        if (!requestURI.contains("/api/auth/login") && !requestURI.contains("/api/auth/register")
                && !requestURI.contains("swagger-ui") && !requestURI.contains("api/v1/docs")) {
            verifyJwtToken(request, authHeader);
        }
    }

    private void verifyJwtToken(HttpServletRequest request, String authHeader) {
        try {

            final String jwt;
            String userEmail;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new AccessDeniedException("Access denied");
            }
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails currentUserDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (Boolean.TRUE.equals(jwtService.isTokenValid(jwt, currentUserDetails))) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            currentUserDetails,
                            null,
                            currentUserDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (AccessDeniedException e) {
            throw new AccessDeniedException("Access denied");
        }

    }
}
