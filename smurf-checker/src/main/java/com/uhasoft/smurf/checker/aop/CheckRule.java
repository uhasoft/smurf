package com.uhasoft.smurf.checker.aop;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class CheckRule {

    private String id;
    /**
     * prerequisite must have default value "true" in case user don't specify it.
     * The condition should be checked by default to make rules as simple as possible.
     */
    private String prerequisite = "true";
    private String condition;
    private String messageId;
    private String[] messageArgs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String[] getMessageArgs() {
        return messageArgs;
    }

    public void setMessageArgs(String[] messageArgs) {
        this.messageArgs = messageArgs;
    }
}
