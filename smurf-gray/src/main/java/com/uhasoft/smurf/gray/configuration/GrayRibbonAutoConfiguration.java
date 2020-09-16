package com.uhasoft.smurf.gray.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.uhasoft.smurf.gray.GrayRule;
import com.uhasoft.smurf.gray.plan.SmurfGrayPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class GrayRibbonAutoConfiguration {

    @Bean
    public IRule grayRule(@Autowired(required = false) List<SmurfGrayPlan> plans, IClientConfig clientConfig){
        return new GrayRule(plans, clientConfig);
    }
}
