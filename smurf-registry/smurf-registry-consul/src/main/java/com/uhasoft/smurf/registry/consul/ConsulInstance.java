package com.uhasoft.smurf.registry.consul;

import com.netflix.loadbalancer.Server;
import com.uhasoft.registry.core.model.SmurfInstance;
import org.springframework.cloud.consul.discovery.ConsulServer;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ConsulInstance extends SmurfInstance {

    private ConsulServer consulServer;

    public ConsulInstance(Server server) {
        super(server.getHost(), server.getPort());
    }

    public ConsulInstance(ConsulServer consulServer) {
        super(consulServer.getHost(), consulServer.getPort());
        this.consulServer = consulServer;
    }

    @Override
    public String getServiceName() {
        return consulServer.getMetaInfo().getAppName();
    }

    @Override
    public String getServiceGroup() {
        return consulServer.getMetaInfo().getServerGroup();
    }

    @Override
    public String getInstanceId() {
        return consulServer.getMetaInfo().getInstanceId();
    }
}
