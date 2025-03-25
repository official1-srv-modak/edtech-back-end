package com.modakdev.modakflix_auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modakdev.models.nonentities.response.auth.ModakFlixBaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class ModakFlixAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(ModakFlixAuthenticationEntryPoint.class);
    private static final String licenceExpiredMsg= "Licence expired. Renewal is required, contact Modak developments";
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ModakFlixBaseResponse res = new ModakFlixBaseResponse();
        res.setMessage(licenceExpiredMsg);
        logger.error(licenceExpiredMsg);
        res.setStatus(HttpStatus.UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(res));
    }
}
