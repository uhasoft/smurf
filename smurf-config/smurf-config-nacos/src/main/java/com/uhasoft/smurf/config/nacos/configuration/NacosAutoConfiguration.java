package com.uhasoft.smurf.config.nacos.configuration;

import com.uhasoft.smurf.config.core.ConfigService;
import com.uhasoft.smurf.config.nacos.NacosConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class NacosAutoConfiguration {

    @Bean
    public ConfigService nacosConfigService(){
        return new NacosConfigService();
    }
}
