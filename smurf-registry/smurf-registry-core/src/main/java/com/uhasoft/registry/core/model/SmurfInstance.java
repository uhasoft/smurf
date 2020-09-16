package com.uhasoft.registry.core.model;

import com.netflix.loadbalancer.Server;

/**
 * @author Weihua
 * @since 1.0.0
 */
public abstract class SmurfInstance extends Server {

    public SmurfInstance(String host, int port) {
        super(host, port);
    }

    public abstract Server getOriginal();

    public abstract String getServiceName();

    public abstract String getServiceGroup();

    public abstract String getInstanceId();

}
