package com.hse.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator gateway(RouteLocatorBuilder rlb,
                                @Value("${routes.storage}")  String storageUri,
                                @Value("${routes.analysis}") String analysisUri) {

        return rlb.routes()

                .route("storage",
                        r -> r.path("/storage/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri(storageUri))

                .route("analysis",
                        r -> r.path("/analysis/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri(analysisUri))

                .build();
    }
}
