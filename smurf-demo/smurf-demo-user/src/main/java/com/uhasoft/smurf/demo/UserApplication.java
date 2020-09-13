package com.uhasoft.smurf.demo;

import com.uhasoft.smurf.starter.server.annotation.SmurfServer;
import org.springframework.boot.SpringApplication;

/**
 * @author Weihua
 * @since 1.0.0
 */
@SmurfServer
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
