package com.uhasoft.smurf.registry.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import com.uhasoft.registry.core.RegistryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.ConsulServer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ConsulRegistryClient implements RegistryClient<ConsulInstance> {

    private ConsulClient consulClient;
    private ConsulDiscoveryProperties properties;

    @Value("${env}")
    private String env;

    public ConsulRegistryClient(ConsulClient consulClient, ConsulDiscoveryProperties properties){
        this.consulClient = consulClient;
        this.properties = properties;
    }

    @PostConstruct
    public void init(){
        this.properties.setDefaultQueryTag("env=" + env);
    }

    @Override
    public List<ConsulInstance> getHealthInstances(String serviceName) {
        String tag = properties.getQueryTagForService(serviceName);
        Response<List<HealthService>> response = consulClient.getHealthServices(serviceName, tag, this.properties.isQueryPassing(),
                createQueryParamsForClientRequest(serviceName), this.properties.getAclToken());
        if (response.getValue() == null || response.getValue().isEmpty()) {
            return Collections.emptyList();
        }
        return transformResponse(response.getValue());
    }

    protected List<ConsulInstance> transformResponse(List<HealthService> healthServices) {
        List<ConsulInstance> servers = new ArrayList<>();
        for (HealthService service : healthServices) {
            ConsulServer server = new ConsulServer(service);
            if (server.getMetadata()
                    .containsKey(this.properties.getDefaultZoneMetadataName())) {
                server.setZone(server.getMetadata()
                        .get(this.properties.getDefaultZoneMetadataName()));
            }
            servers.add(new ConsulInstance(server));
        }
        return servers;
    }

    protected QueryParams createQueryParamsForClientRequest(String serviceName) {
        String dataCenter = getDataCenter(serviceName);
        if (dataCenter != null) {
            return new QueryParams(dataCenter, this.properties.getConsistencyMode());
        }
        return new QueryParams(this.properties.getConsistencyMode());
    }

    protected String getDataCenter(String serviceName) {
        return this.properties.getDatacenters().get(serviceName);
    }

}
