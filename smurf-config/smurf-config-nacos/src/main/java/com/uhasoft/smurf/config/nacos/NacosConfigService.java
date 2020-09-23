package com.uhasoft.smurf.config.nacos;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.uhasoft.smurf.config.core.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class NacosConfigService implements ConfigService {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public void observe(String type, String key, Consumer<String> observer) {
        try {
            nacosConfigManager.getConfigService().addListener(type, "DEFAULT_GROUP", new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    observer.accept(configInfo);
                }
            });
            String value = nacosConfigManager.getConfigService().getConfig(type, "DEFAULT_GROUP", 10000L);
            observer.accept(value);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

}
