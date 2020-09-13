package com.uhasoft.smurf.demo.book;

import com.uhasoft.smurf.starter.server.annotation.SmurfServer;
import org.springframework.boot.SpringApplication;

/**
 * @author Weihua
 * @since 1.0.0
 */
@SmurfServer
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}
