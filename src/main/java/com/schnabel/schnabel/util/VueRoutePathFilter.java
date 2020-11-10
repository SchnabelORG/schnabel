
package com.schnabel.schnabel.util;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.util.ArrayList;

@Component
class VueRoutePathFilter implements WebFilter {

        private ArrayList<String> BOOT_PATHS = new ArrayList<String>(){{
            add("/actuator/");
            add("/api/");
            add("/js/");
            add("/css/");
            add("/img/");

        }};


        private final String SPA_PATH = "/index.html";

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
                    .mutate().path(SPA_PATH)
                    .build())
                .build());
    }

    private boolean isApiPath(String path) {
        for(String p : BOOT_PATHS) {
            if(path.startsWith(p))
            {
                return true;
            }
        }
        return false;
    }
}