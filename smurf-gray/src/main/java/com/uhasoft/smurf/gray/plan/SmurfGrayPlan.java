package com.uhasoft.smurf.gray.plan;

import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public interface SmurfGrayPlan {

    int getOrder();

    String getName();

    boolean isQualified();

    List<Server> filter(String serviceName, List<Server> instances);
}
