package com.uhasoft.smurf.registry.consul.configuration;

import com.ecwid.consul.v1.ConsulClient;
import com.uhasoft.registry.core.RegistryClient;
import com.uhasoft.smurf.registry.consul.ConsulInstance;
import com.uhasoft.smurf.registry.consul.ConsulRegistryClient;
import com.uhasoft.smurf.registry.consul.RegistrationCustomizer;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class ConsulAutoConfiguration {

    @Bean
    public RegistryClient<ConsulInstance> registryClient(ConsulClient client, ConsulDiscoveryProperties properties){
        return new ConsulRegistryClient(client, properties);
    }

    @Bean
    public ConsulRegistrationCustomizer consulRegistrationCustomizer(){
        return new RegistrationCustomizer();
    }

}
