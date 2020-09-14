package com.uhasoft.smurf.registry.consul;

import com.uhasoft.registry.core.RegistryClient;
import com.uhasoft.registry.core.SmurfServerList;

import java.util.Collections;
import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ConsulServerList extends SmurfServerList<ConsulInstance> {

    private RegistryClient<ConsulInstance> client;

    public ConsulServerList(RegistryClient<ConsulInstance> client){
        this.client = client;
    }

    @Override
    protected List<ConsulInstance> getServers() {
        if (this.client == null) {
            return Collections.emptyList();
        }
        return client.getHealthInstances(getServiceId());
    }

}
