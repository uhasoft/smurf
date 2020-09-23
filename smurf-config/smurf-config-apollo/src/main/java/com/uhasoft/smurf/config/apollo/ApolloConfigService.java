package com.uhasoft.smurf.config.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.uhasoft.smurf.config.core.ConfigService;

import java.util.function.Consumer;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ApolloConfigService implements ConfigService {

    @Override
    public void observe(String type, String key, Consumer<String> consumer) {
        getConfig(type).addChangeListener(e -> {
            ConfigChange change = e.getChange(key);
            String newValue = change.getNewValue();
            consumer.accept(newValue);
        });
        consumer.accept(getValue(type, key));
    }

    public Config getConfig(String namespace){
        return com.ctrip.framework.apollo.ConfigService.getConfig(namespace);
    }

    public String getValue(String namespace, String key){
        return getConfig(namespace).getProperty(key, null);
    }
}
