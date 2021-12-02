package be.d2l.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
public class AuthorizeFilter extends
        AbstractGatewayFilterFactory<AuthorizeFilter.Config> {

    public AuthorizeFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            System.out.println(headers.containsKey("Authorization"));
            if(!headers.containsKey("Authorization") || !config.verify(headers.getFirst("Authorization")))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            return chain.filter(exchange).then();
        };
    }

    public static class Config {

        private Algorithm jwtAlgo;
        private JWTVerifier verifier;

        private String JWTSecret;

        public Config(String JWTSecret) {
            this.JWTSecret = JWTSecret;
            this.jwtAlgo = Algorithm.HMAC256(JWTSecret);
            this.verifier = JWT.require(jwtAlgo).withIssuer("auth0").build();
        }

        public boolean verify(String authorization) {
            try {
                System.out.println(authorization);
                verifier.verify(authorization);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }
}
