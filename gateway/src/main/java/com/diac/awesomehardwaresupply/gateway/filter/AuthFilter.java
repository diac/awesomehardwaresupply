package com.diac.awesomehardwaresupply.gateway.filter;

import com.diac.awesomehardwaresupply.gateway.service.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private static final String BEARER_SUBHEADER = "Bearer ";

    private final RouteValidator routeValidator;

    private final JwtService jwtService;

    public AuthFilter(
            RouteValidator routeValidator,
            JwtService jwtService
    ) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured().test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    System.out.println("Authorization header is missing");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authorization header is missing");
                }
                String token =
                        Optional.ofNullable(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0))
                                .map(authHeader -> authHeader.replaceFirst(BEARER_SUBHEADER, ""))
                                .orElseThrow(
                                        () -> new ResponseStatusException(
                                                HttpStatus.BAD_REQUEST,
                                                "Authorization header is missing"
                                        )
                                );
                try {
                    jwtService.validateToken(token);
                } catch (Exception e) {
                    throw new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Unauthorized"
                    );
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}