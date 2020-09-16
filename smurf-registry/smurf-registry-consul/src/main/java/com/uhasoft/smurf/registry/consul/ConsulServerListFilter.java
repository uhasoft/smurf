package com.uhasoft.smurf.registry.consul;

import com.netflix.loadbalancer.ServerListFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.consul.discovery.ConsulServer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ConsulServerListFilter implements ServerListFilter<ConsulInstance> {

    private static final Logger logger = LoggerFactory.getLogger(ConsulServerListFilter.class);

    @Override
    public List<ConsulInstance> getFilteredListOfServers(List<ConsulInstance> servers) {
        List<ConsulInstance> filtered = new ArrayList<>();

        for (ConsulInstance server : servers) {
            if (server.getOriginal() instanceof ConsulServer) {
                ConsulServer consulServer = (ConsulServer) server.getOriginal();

                if (consulServer.isPassingChecks()) {
                    filtered.add(server);
                }

            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Unable to determine aliveness of server type {}, {}", server.getClass(), server);
                }
                filtered.add(server);
            }
        }

        return filtered;
    }

}
