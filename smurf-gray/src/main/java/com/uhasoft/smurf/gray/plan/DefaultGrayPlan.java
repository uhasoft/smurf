package com.uhasoft.smurf.gray.plan;

import com.netflix.loadbalancer.Server;
import com.uhasoft.smurf.core.ServerInfo;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class DefaultGrayPlan implements SmurfGrayPlan {

    private Map<String, List<ClientRouteRule>> rulesMap;

    private ServerInfo serverInfo;

    private SpelExpressionParser parser = new SpelExpressionParser();

    public DefaultGrayPlan(ServerInfo serverInfo, Map<String, List<ClientRouteRule>> rulesMap){
        this.serverInfo = serverInfo;
        this.rulesMap = rulesMap;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isQualified(String serviceName) {
        return !CollectionUtils.isEmpty(rulesMap) && !CollectionUtils.isEmpty(rulesMap.get(serviceName));
    }

    @Override
    public List<Server> filter(String serviceName, List<Server> instances) {
        List<ClientRouteRule> rules = rulesMap.get(serviceName);
        for(ClientRouteRule rule : rules){
            StandardEvaluationContext context = new StandardEvaluationContext();
            Map<String, String> params = Collections.singletonMap("port", serverInfo.getPort());
            context.setVariable("c", params);
            if(parser.parseExpression(rule.getClientCondition()).getValue(context, Boolean.class)){
                List<Server> filtered = new ArrayList<>();
                for(Server server : instances){
                    if(evaluate(rule.getServerCondition(), server)){
                        filtered.add(server);
                    }
                }
                return filtered;
            }
        }
        return null;
    }

    private boolean evaluate(String expression, Server server){
        StandardEvaluationContext context = new StandardEvaluationContext();
        Map<String, String> params = Collections.singletonMap("port", server.getPort() + "");
        context.setVariable("s", params);
        return parser.parseExpression(expression).getValue(context, Boolean.class);
    }

}
