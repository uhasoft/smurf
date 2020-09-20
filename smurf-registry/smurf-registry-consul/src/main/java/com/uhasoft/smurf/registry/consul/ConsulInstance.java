package com.uhasoft.smurf.registry.consul;

import com.netflix.loadbalancer.Server;
import com.uhasoft.registry.core.model.SmurfInstance;
import org.springframework.cloud.consul.discovery.ConsulServer;

import java.util.Map;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ConsulInstance extends SmurfInstance {

    private Server server;

    public ConsulInstance(Server server) {
        super(server.getHost(), server.getPort());
    }

    public ConsulInstance(ConsulServer consulServer) {
        super(consulServer.getHost(), consulServer.getPort());
        this.setSchemea(consulServer.getScheme());
        this.server = consulServer;
    }

    @Override
    public Server getOriginal() {
        return server;
    }

    @Override
    public String getServiceName() {
        return server.getMetaInfo().getAppName();
    }

    @Override
    public String getServiceGroup() {
        return server.getMetaInfo().getServerGroup();
    }

    @Override
    public String getInstanceId() {
        return server.getMetaInfo().getInstanceId();
    }

    @Override
    public Map<String, String> getMetadata() {
        return ((ConsulServer)server).getMetadata();
    }
}
