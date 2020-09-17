package com.uhasoft.smurf.core.configuration;

import com.uhasoft.smurf.core.ServerInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class CoreAutoConfiguration {

    @Bean
    public ServerInfo serverInfo(){
        return new ServerInfo();
    }
}
