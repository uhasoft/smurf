package com.uhasoft.registry.core;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.uhasoft.registry.core.model.SmurfInstance;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public abstract class SmurfServerList<T extends SmurfInstance> extends AbstractServerList<T> {

    private String serviceId;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        this.serviceId = clientConfig.getClientName();
    }

    @Override
    public List<T> getInitialListOfServers() {
        return getServers();
    }

    @Override
    public List<T> getUpdatedListOfServers() {
        return getServers();
    }

    public String getServiceId(){
        return serviceId;
    }

    protected abstract List<T> getServers();
}
