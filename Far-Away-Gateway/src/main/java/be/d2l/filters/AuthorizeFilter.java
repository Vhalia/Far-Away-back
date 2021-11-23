package be.d2l.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthorizeFilter extends
        AbstractGatewayFilterFactory<AuthorizeFilter.Config> {

    public AuthorizeFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            //Filtre AVANT envoi de la requête ici
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //Filtre APRES envoi de la réponse ici
            }));
        };
    }

    public static class Config {

    }
}
