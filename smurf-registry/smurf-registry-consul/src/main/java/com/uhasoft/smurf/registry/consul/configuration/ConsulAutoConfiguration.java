package com.uhasoft.smurf.registry.consul.configuration;

import com.uhasoft.smurf.registry.consul.RegistrationCustomizer;
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
    public ConsulRegistrationCustomizer consulRegistrationCustomizer(){
        return new RegistrationCustomizer();
    }
}
