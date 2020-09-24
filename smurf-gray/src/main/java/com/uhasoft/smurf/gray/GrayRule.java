package com.uhasoft.smurf.gray;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import com.uhasoft.smurf.gray.plan.SmurfGrayPlan;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class GrayRule implements IRule {

    private IClientConfig clientConfig;

    private ILoadBalancer loadBalancer;

    private List<SmurfGrayPlan> plans;

    private AvailabilityPredicate availabilityPredicate;

    public GrayRule(List<SmurfGrayPlan> plans, IClientConfig clientConfig){
        this.plans = plans;
        this.clientConfig = clientConfig;
        this.availabilityPredicate = new AvailabilityPredicate(this, clientConfig);
    }

    @Override
    public Server choose(Object key) {
        String serviceName = clientConfig.getClientName();
        if(!CollectionUtils.isEmpty(plans)){
            for(SmurfGrayPlan plan : plans){
                if(plan.isQualified(serviceName)){
                    List<Server> instances = plan.filter(serviceName, loadBalancer.getReachableServers());
                    if(!CollectionUtils.isEmpty(instances)){
                        return availabilityPredicate.chooseRandomlyAfterFiltering(instances).orNull();
                    }
                }
            }
        }
        return availabilityPredicate.chooseRandomlyAfterFiltering(loadBalancer.getReachableServers()).orNull();
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.loadBalancer = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

}
