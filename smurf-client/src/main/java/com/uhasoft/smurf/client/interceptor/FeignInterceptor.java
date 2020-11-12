package com.uhasoft.smurf.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class FeignInterceptor implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void apply(RequestTemplate template) {
        template.header("last-step", applicationName);
    }
}
