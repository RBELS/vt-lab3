package com.adbs.vtlabs.lab3.config.security;

import com.adbs.vtlabs.lab3.exception.ErrorCode;
import com.adbs.vtlabs.lab3.model.config.ErrorResponse;
import com.adbs.vtlabs.lab3.service.AuthorityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component("securityFilter")
public class SecurityFilter extends GenericFilterBean {
    private final AuthorityService authorityService;
    private final ObjectMapper objectMapper;

    private static final Set<String> ALLOWED_URLS = Set.of("/auth/login", "/auth/register");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");
        if (ALLOWED_URLS.contains(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (
                Objects.isNull(authHeader)
                || !authHeader.startsWith("Bearer ")
                || !authorityService.verifyUserJwt(authHeader.substring("Bearer ".length()))
        ) {
            ErrorResponse errorResponse = ErrorResponse.fromErrorStatus(ErrorCode.AUTH_ERROR);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setStatus(errorResponse.getCode());
            res.getWriter().println(objectMapper.writeValueAsString(errorResponse));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
