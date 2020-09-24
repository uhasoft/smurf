package com.uhasoft.smurf.registry.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class SmurfConsulClient extends ConsulClient {

    @Value("${env}")
    private String env;

    @Override
    public Response<List<HealthService>> getHealthServices(String serviceName, boolean onlyPassing, QueryParams queryParams) {
        return getHealthServices(serviceName, "env=" + env, onlyPassing, queryParams);
    }

    @Override
    public Response<List<HealthService>> getHealthServices(String serviceName, boolean onlyPassing, QueryParams queryParams, String token) {
        return getHealthServices(serviceName, "env=" + env, onlyPassing, queryParams, token);
    }

}
