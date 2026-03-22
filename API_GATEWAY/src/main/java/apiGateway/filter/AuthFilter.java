package apiGateway.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Validator validator;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            if (validator.predicate.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Authorization header is missing"
                    );
                }

                String authHeader = exchange.getRequest().getHeaders()
                        .get(HttpHeaders.AUTHORIZATION).get(0);

                String token = null;

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }

                // validate token
                try {
                    jwtUtil.validateToken(token);
                } catch (ExpiredJwtException ex) {
                    throw new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Token expired. Please login again"
                    );
                } catch (Exception ex) {
                    throw new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Invalid token"
                    );
                }
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}