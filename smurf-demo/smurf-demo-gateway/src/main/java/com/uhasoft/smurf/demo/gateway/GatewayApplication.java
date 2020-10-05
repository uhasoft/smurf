package com.uhasoft.smurf.demo.gateway;

import com.uhasoft.smurf.starter.gateway.annotation.SmurfGateway;
import org.springframework.boot.SpringApplication;

/**
 * @author Weihua
 * @since 1.0.0
 */
@SmurfGateway
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
