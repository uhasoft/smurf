package com.uhasoft.registry.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.Registration;

import java.util.Map;


/**
 * @author Weihua
 * @since 1.0.0
 */
public class RegistryServer {

    @Autowired
    private Registration registration;

    public int getPort() {
        return registration.getPort();
    }

    public String getHost(){
        return registration.getHost();
    }

    public String getInstanceId(){
        return registration.getInstanceId();
    }

    public Map<String, String> getMetadata(){
        return registration.getMetadata();
    }

    public String getCluster(){
        return registration.getMetadata().get("cluster");
    }

    public String getVersion(){
        return registration.getMetadata().get("version");
    }

}
