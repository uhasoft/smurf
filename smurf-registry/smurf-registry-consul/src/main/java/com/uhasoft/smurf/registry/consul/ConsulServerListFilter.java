package com.uhasoft.smurf.registry.consul;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.consul.discovery.ConsulServer;
import org.springframework.cloud.consul.discovery.HealthServiceServerListFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ConsulServerListFilter implements ServerListFilter<ConsulInstance> {

    private static final Log log = LogFactory.getLog(HealthServiceServerListFilter.class);

    @Override
    public List<ConsulInstance> getFilteredListOfServers(List<ConsulInstance> servers) {
        List<ConsulInstance> filtered = new ArrayList<>();

        for (Server server : servers) {
            if (server instanceof ConsulServer) {
                ConsulServer consulServer = (ConsulServer) server;

                if (consulServer.isPassingChecks()) {
                    filtered.add(new ConsulInstance(consulServer));
                }

            }
            else {
                if (log.isDebugEnabled()) {
                    log.debug("Unable to determine aliveness of server type "
                            + server.getClass() + ", " + server);
                }
                filtered.add(new ConsulInstance(server));
            }
        }

        return filtered;
    }
}
