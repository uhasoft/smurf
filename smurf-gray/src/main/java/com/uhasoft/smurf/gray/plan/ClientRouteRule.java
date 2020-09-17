package com.uhasoft.smurf.gray.plan;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ClientRouteRule {

    private String clientCondition;
    private String serverCondition;

    public String getClientCondition() {
        return clientCondition;
    }

    public void setClientCondition(String clientCondition) {
        this.clientCondition = clientCondition;
    }

    public String getServerCondition() {
        return serverCondition;
    }

    public void setServerCondition(String serverCondition) {
        this.serverCondition = serverCondition;
    }
}
