package com.uhasoft.registry.core.configuration;

import com.uhasoft.registry.core.MetaCustomizer;
import com.uhasoft.registry.core.RegistryServer;
import com.uhasoft.registry.core.TagCustomizer;
import com.uhasoft.smurf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class RegistryAutoConfiguration {

    @Value("#{'${smurf.registry.metadata:}'.split(',')}")
    private List<String> metadata;

    @Bean
    public RegistryServer registryServer(){
        return new RegistryServer();
    }

    @Bean
    public MetaCustomizer metaCustomizer(){
        Map<String, String> map = new HashMap<>();
        if(!CollectionUtils.isEmpty(metadata)){
            for(String element : metadata){
                if(StringUtils.hasText(element) && element.contains("=")){
                    String[] kv = element.split("=");
                    map.put(kv[0].trim(), kv[1].trim());
                }
            }
        }
        return () -> map;
    }

    @Bean
    public TagCustomizer tagCustomizer(){
        List<String> tags = new ArrayList<>();
        if(!CollectionUtils.isEmpty(metadata)){
            for(String element : metadata){
                if(StringUtils.hasText(element)){
                    tags.add(element);
                }
            }
        }
        return () -> tags;
    }
}
