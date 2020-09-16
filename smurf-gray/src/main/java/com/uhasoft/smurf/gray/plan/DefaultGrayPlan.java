package com.uhasoft.smurf.gray.plan;

import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class DefaultGrayPlan implements SmurfGrayPlan {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isQualified() {
        return false;
    }

    @Override
    public List<Server> filter(String serviceName, List<Server> instances) {
        return null;
    }

}
