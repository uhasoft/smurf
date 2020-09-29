package com.uhasoft.smurf.ratelimit.core.model;

import java.util.Map;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class Rule {

    private String resource;
    private int defaultThreshold;
    private Map<String, Integer> originThreshold;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getDefaultThreshold() {
        return defaultThreshold;
    }

    public void setDefaultThreshold(int defaultThreshold) {
        this.defaultThreshold = defaultThreshold;
    }

    public Map<String, Integer> getOriginThreshold() {
        return originThreshold;
    }

    public void setOriginThreshold(Map<String, Integer> originThreshold) {
        this.originThreshold = originThreshold;
    }
}
