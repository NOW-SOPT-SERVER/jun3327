package org.sopt.practice.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.common.dto.ErrorResponse;
import org.sopt.practice.exception.UnauthorizedException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        setResponse(response, request);
    }

    private void setResponse(HttpServletResponse response, HttpServletRequest request) throws IOException {
        UnauthorizedException exception = (UnauthorizedException) request.getAttribute("exception");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter()
//                .write(objectMapper.writeValueAsString(
//                        ErrorResponse.of(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION.getStatus(),
//                                ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION.getMessage())));
                .write(objectMapper.writeValueAsString(
                        ErrorResponse.of(exception.getErrorMessage().getStatus(),
                                exception.getErrorMessage().getMessage())));
    }
}
