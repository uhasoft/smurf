package com.uhasoft.smurf.gateway.scg.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class ScgAutoConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                    .route("order", r -> r
                    .path("/order/**")
                    .filters(f -> f.stripPrefix(1))
                    .uri("lb://demo-order"))
                .build();
    }
}
