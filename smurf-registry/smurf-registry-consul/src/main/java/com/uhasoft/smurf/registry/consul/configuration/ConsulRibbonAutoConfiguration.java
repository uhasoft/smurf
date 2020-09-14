package com.uhasoft.smurf.registry.consul.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ServerList;
import com.uhasoft.registry.core.RegistryClient;
import com.uhasoft.registry.core.SmurfServerList;
import com.uhasoft.smurf.registry.consul.ConsulInstance;
import com.uhasoft.smurf.registry.consul.ConsulServerList;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.consul.discovery.ConsulRibbonClientConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
@AutoConfigureAfter(RibbonAutoConfiguration.class)
@RibbonClients(defaultConfiguration = ConsulRibbonClientConfiguration.class)
public class ConsulRibbonAutoConfiguration {

    @Bean
    public ServerList<ConsulInstance> ribbonServerList(IClientConfig config, RegistryClient<ConsulInstance> client) {
        SmurfServerList<ConsulInstance> serverList = new ConsulServerList(client);
        serverList.initWithNiwsConfig(config);
        return serverList;
    }

}
