package com.uhasoft.smurf.config.apollo.configuration;

import com.uhasoft.smurf.config.apollo.ApolloConfigService;
import com.uhasoft.smurf.config.core.ConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class ApolloAutoConfiguration {

    @Bean
    public ConfigService apolloConfigService(){
        return new ApolloConfigService();
    }
}
