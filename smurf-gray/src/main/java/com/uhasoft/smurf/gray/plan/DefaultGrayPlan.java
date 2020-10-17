package com.uhasoft.smurf.gray.plan;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netflix.loadbalancer.Server;
import com.uhasoft.registry.core.model.SmurfInstance;
import com.uhasoft.registry.core.RegistryServer;
import com.uhasoft.smurf.config.core.ConfigService;
import com.uhasoft.smurf.core.context.RequestContext;
import com.uhasoft.smurf.common.util.SpelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.uhasoft.smurf.gray.constant.GrayConstant.CLIENT_VARIABLE;
import static com.uhasoft.smurf.gray.constant.GrayConstant.HOST;
import static com.uhasoft.smurf.gray.constant.GrayConstant.INSTANCE_ID;
import static com.uhasoft.smurf.gray.constant.GrayConstant.PORT;
import static com.uhasoft.smurf.gray.constant.GrayConstant.REQUEST_VARIABLE;
import static com.uhasoft.smurf.gray.constant.GrayConstant.SERVER_VARIABLE;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class DefaultGrayPlan implements SmurfGrayPlan {

    private static final Logger logger = LoggerFactory.getLogger(DefaultGrayPlan.class);

    private Map<String, List<ClientRouteRule>> rulesMap;

    @Autowired
    private RegistryServer registryServer;

    @Autowired
    private ConfigService configService;

    @PostConstruct
    public void init(){
        configService.observe("smurf.gray." + registryServer.getServiceName(), "smurf.gray.rules", value -> {
            logger.info("Gray rules: {}", value);
            if(value != null){
                Type type = new TypeToken<Map<String, List<ClientRouteRule>>>(){}.getType();
                this.rulesMap = new Gson().fromJson(value, type);
                logger.info("Loaded gray rules for {} services.", rulesMap.size());
            }
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String getName() {
        return "Client-Defined Gray Plan";
    }

    @Override
    public boolean isQualified(String serviceName) {
        boolean qualified = !CollectionUtils.isEmpty(rulesMap) && !CollectionUtils.isEmpty(rulesMap.get(serviceName));
        if(qualified){
            logger.debug("{} qualifies {}", serviceName, getName());
        } else {
            logger.debug("{} doesn't qualify {}", serviceName, getName());
        }
        return qualified;
    }

    @Override
    public List<Server> filter(String serviceName, List<Server> instances) {
        List<ClientRouteRule> rules = rulesMap.get(serviceName);
        for(ClientRouteRule rule : rules){
            logger.debug("Evaluating rule [{}]", rule.getName());
            StandardEvaluationContext context = new StandardEvaluationContext();
            Map<String, Object> params = new HashMap<>(registryServer.getMetadata());
            params.put(HOST, registryServer.getPort() + "");
            params.put(PORT, registryServer.getHost());
            params.put(INSTANCE_ID, registryServer.getInstanceId());
            context.setVariable(CLIENT_VARIABLE, params);
            context.setVariable(REQUEST_VARIABLE, RequestContext.getHeaders());
            logger.debug("#{} in context:[{}]", CLIENT_VARIABLE, params);
            logger.debug("#{} in context:[{}]", REQUEST_VARIABLE, RequestContext.getHeaders());
            if(SpelUtil.eval(rule.getClientCondition(), context)){
                List<Server> filtered = new ArrayList<>();
                for(Server instance : instances){
                    logger.debug("Evaluating server instance: [{}]", instance.getHostPort());
                    if(evaluate(rule.getServerCondition(), instance)){
                        filtered.add(instance);
                    }
                }
                if(!CollectionUtils.isEmpty(filtered)){
                    return filtered;
                }
            }
        }
        return null;
    }

    private boolean evaluate(String expression, Server server){
        StandardEvaluationContext context = new StandardEvaluationContext();
        Map<String, String> params = new HashMap<>();
        params.put(HOST, server.getHost());
        params.put(PORT, server.getPort() + "");
        params.put(INSTANCE_ID, server.getId());
        params.putAll(((SmurfInstance)server).getMetadata());
        context.setVariable(SERVER_VARIABLE, params);
        logger.debug("#{} in context:[{}]", SERVER_VARIABLE, params);
        return SpelUtil.eval(expression, context);
    }

}
