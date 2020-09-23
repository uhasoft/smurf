package com.uhasoft.smurf.config.core;

import java.util.function.Consumer;

/**
 * @author Weihua
 * @since 1.0.0
 */
public interface ConfigService {

    void observe(String type, String key, Consumer<String> observer);

}
