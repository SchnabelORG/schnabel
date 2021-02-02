
package com.schnabel.schnabel.util;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.util.ArrayList;

/**
 * A WebFilter used for routing Vue calls in SPA applications
 * 
 */
@Component
class VueRoutePathFilter implements WebFilter
{

    private final ArrayList<String> bootPaths = new ArrayList<String>()
    {
        private static final long serialVersionUID = 1929150336885770015L;
        {
            add("/actuator/");
            add("/api/");
            add("/js/");
            add("/css/");
            add("/img/");

        }
    };

    private static final String spaPath = "/index.html";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                        WebFilterChain chain) {
        if (isApiPath(exchange.getRequest().getPath().value())) {
            return chain.filter(exchange);
        }

        return chain
            .filter(exchange
                .mutate()
                .request(exchange.getRequest()
                    .mutate().path(spaPath)
                    .build())
                .build());
    }

    private boolean isApiPath(String path) {
        for(String p : bootPaths) {
            if(path.startsWith(p))
            {
                return true;
            }
        }
        return false;
    }
}