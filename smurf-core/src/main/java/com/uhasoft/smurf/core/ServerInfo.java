package com.uhasoft.smurf.core;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ServerInfo {

    @Value("${server.port}")
    private String port;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
