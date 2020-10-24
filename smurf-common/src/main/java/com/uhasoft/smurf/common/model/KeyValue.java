package com.uhasoft.smurf.common.model;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class KeyValue {

    private String key;
    private String value;

    public KeyValue(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString(){
        return String.format("{\"key\":\"%s\",\"value\":\"%s\"}", key, value);
    }

}
