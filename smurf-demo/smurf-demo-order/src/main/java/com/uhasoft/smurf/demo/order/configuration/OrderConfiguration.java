package com.uhasoft.smurf.demo.order.configuration;

import com.uhasoft.registry.core.RegistryServer;
import com.uhasoft.smurf.gray.plan.ClientRouteRule;
import com.uhasoft.smurf.gray.plan.DefaultGrayPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class OrderConfiguration {

    @Autowired
    private RegistryServer registryServer;

    @Bean
    public DefaultGrayPlan defaultGrayPlan(){
        Map<String, List<ClientRouteRule>> rulesMap = new HashMap<>();
        List<ClientRouteRule> rules = new ArrayList<>();
        {
            ClientRouteRule rule = new ClientRouteRule();
            rule.setName("Shanghai Rule");
            rule.setClientCondition("#r['s-g-area'] == 'shanghai'");
            rule.setServerCondition("#s['area'] == 'shanghai'");
            rules.add(rule);
        }
        {
            ClientRouteRule rule = new ClientRouteRule();
            rule.setName("Beijing Rule");
            rule.setClientCondition("#r['s-g-area'] == 'beijing'");
            rule.setServerCondition("#s['area'] == 'beijing'");
            rules.add(rule);
        }
//        {
//            ClientRouteRule rule = new ClientRouteRule();
//            rule.setClientCondition("#c['port'] == '8203'");
//            rule.setServerCondition("#s['port'] == '8303'");
//            rules.add(rule);
//        }
        rulesMap.put("demo-book", rules);

        return new DefaultGrayPlan(registryServer, rulesMap);
    }
}
