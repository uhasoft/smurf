package com.uhasoft.smurf.ratelimit.sentinel.configuration;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uhasoft.registry.core.RegistryServer;
import com.uhasoft.smurf.config.core.ConfigService;
import com.uhasoft.smurf.ratelimit.core.OriginParser;
import com.uhasoft.smurf.ratelimit.core.model.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.uhasoft.smurf.ratelimit.core.constant.RateLimitConstant.DEFAULT_ORIGIN;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class SentinelAutoConfiguration implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(SentinelAutoConfiguration.class);

    @Autowired
    private OriginParser<HttpServletRequest> originParser;

    @Autowired
    private ConfigService configService;

    @Autowired
    private RegistryServer registryServer;

    @PostConstruct
    public void init(){
        configService.observe("smurf.ratelimit." + registryServer.getServiceName(), "smurf.ratelimit.rules", value -> {
            logger.info("RateLimit rules: {}", value);
            if(value != null){
                Type type = new TypeToken<List<Rule>>(){}.getType();
                List<Rule> rules = new Gson().fromJson(value, type);
                List<FlowRule> flowRules = new ArrayList<>();
                for(Rule rule : rules){
                    if(rule.getDefaultThreshold() > 0){
                        FlowRule flowRule = new FlowRule();
                        flowRule.setCount(rule.getDefaultThreshold());
                        flowRule.setResource(rule.getResource());
                        flowRule.setLimitApp(DEFAULT_ORIGIN);
                        flowRules.add(flowRule);
                    }
                    if(!CollectionUtils.isEmpty(rule.getOriginThreshold())){
                        for(String origin : rule.getOriginThreshold().keySet()){
                            FlowRule flowRule = new FlowRule();
                            flowRule.setCount(rule.getOriginThreshold().get(origin));
                            flowRule.setResource(rule.getResource());
                            flowRule.setLimitApp(origin);
                            flowRules.add(flowRule);
                        }
                    }
                }
                FlowRuleManager.loadRules(flowRules);
                logger.info("Loaded {} ratelimit rules for {} APIs.", flowRules.size(), rules.size());
            }
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();
        // Enable the HTTP method prefix.
        config.setHttpMethodSpecify(true);
        config.setOriginParser(request -> originParser.parse(request));
        // Add to the interceptor list.
        registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");
    }
}
