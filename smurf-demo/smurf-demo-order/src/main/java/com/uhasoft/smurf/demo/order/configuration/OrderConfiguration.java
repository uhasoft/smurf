package com.uhasoft.smurf.demo.order.configuration;

import com.uhasoft.smurf.core.ServerInfo;
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
    private ServerInfo serverInfo;

    @Bean
    public DefaultGrayPlan defaultGrayPlan(){
        Map<String, List<ClientRouteRule>> rulesMap = new HashMap<>();
        List<ClientRouteRule> rules = new ArrayList<>();
        {
            ClientRouteRule rule = new ClientRouteRule();
            rule.setClientCondition("true");
            rule.setServerCondition("#s['port'] == '8301'");
            rules.add(rule);
        }
//        {
//            ClientRouteRule rule = new ClientRouteRule();
//            rule.setClientCondition("#c['port'] == '8202'");
//            rule.setServerCondition("#s['port'] == '8302'");
//            rules.add(rule);
//        }
//        {
//            ClientRouteRule rule = new ClientRouteRule();
//            rule.setClientCondition("#c['port'] == '8203'");
//            rule.setServerCondition("#s['port'] == '8303'");
//            rules.add(rule);
//        }
        rulesMap.put("demo-book", rules);

        return new DefaultGrayPlan(serverInfo, rulesMap);
    }
}
