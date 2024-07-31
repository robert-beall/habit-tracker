package com.rjb.hobby_tracker.auth;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class defines an endpoint for handling a request made to the application without proper authorization.
 */
@Component
public class UnauthorizedEntrypoint implements AuthenticationEntryPoint {
    /**
     * Endpoint handles unauthorized access to application. 
     * 
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
    AuthenticationException authException) throws IOException, ServletException {
        // Sets unauthorized status code 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}