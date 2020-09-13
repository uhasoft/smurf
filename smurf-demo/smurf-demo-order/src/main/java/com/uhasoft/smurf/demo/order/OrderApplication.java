package com.uhasoft.smurf.demo.order;

import com.uhasoft.smurf.starter.server.annotation.SmurfServer;
import org.springframework.boot.SpringApplication;

/**
 * @author Weihua
 * @since 1.0.0
 */
@SmurfServer
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
