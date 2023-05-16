package com.diac.awesomehardwaresupply.gateway.filter;

import com.diac.awesomehardwaresupply.domain.enumeration.Authority;
import com.diac.awesomehardwaresupply.gateway.model.AclRecord;
import com.diac.awesomehardwaresupply.gateway.service.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private static final String BEARER_SUBHEADER = "Bearer ";

    private static final List<HttpMethod> HTTP_READ_METHODS = List.of(HttpMethod.GET);

    private static final List<HttpMethod> HTTP_WRITE_METHODS = List.of(
            HttpMethod.GET,
            HttpMethod.POST,
            HttpMethod.PUT,
            HttpMethod.PATCH,
            HttpMethod.DELETE
    );

    private static final List<AclRecord> ACL = List.of(
            new AclRecord("/knowledgebase", HTTP_READ_METHODS, Authority.KNOWLEDGEBASE_READ),
            new AclRecord("/knowledgebase", HTTP_WRITE_METHODS, Authority.KNOWLEDGEBASE_WRITE),
            new AclRecord("/price_schedule", HTTP_READ_METHODS, Authority.PRICE_SCHEDULE_READ),
            new AclRecord("/price_schedule", HTTP_WRITE_METHODS, Authority.PRICE_SCHEDULE_WRITE)
    );

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
                List<Authority> authorities = jwtService.getAuthoritiesFromToken(token);
                List<AclRecord> aclMatches = ACL.stream()
                        .filter(
                                aclRecord ->
                                        exchange.getRequest().getPath().toString().startsWith(aclRecord.uri())
                                                && aclRecord.httpMethods().contains(exchange.getRequest().getMethod())
                                                && authorities.contains(aclRecord.requiredAuthority())
                        ).toList();
                if (aclMatches.isEmpty()) {
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