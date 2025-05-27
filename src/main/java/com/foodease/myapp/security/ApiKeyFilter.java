package com.foodease.myapp.security;

import com.foodease.myapp.config.ApiKeyProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private final ApiKeyProperties props;

    public ApiKeyFilter(ApiKeyProperties props) {
        this.props = props;
    }

    private static final List<String> EXCLUDE = List.of(
            "/swagger-ui",        // catches /swagger-ui/**
            "/v3/api-docs",       // catches /v3/api-docs/**
            "/webjars"            // catches /webjars/**
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDE.stream().anyMatch(path::startsWith);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        String apiKey = req.getHeader("X-API-KEY");
        if (props.getKey().equals(apiKey)) {
            // có thể thêm check signature trên body hoặc timestamp bằng props.getSecret()
            chain.doFilter(req, res);
        } else {
            res.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid API Key");
        }
    }
}
