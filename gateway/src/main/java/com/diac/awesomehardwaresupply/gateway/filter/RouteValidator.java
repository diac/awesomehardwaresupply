package com.diac.awesomehardwaresupply.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final List<String> PUBLIC_API_ENDPOINTS = List.of(
            "/auth/register",
            "/auth/sign-in",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured() {
        return request -> PUBLIC_API_ENDPOINTS
                .stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}