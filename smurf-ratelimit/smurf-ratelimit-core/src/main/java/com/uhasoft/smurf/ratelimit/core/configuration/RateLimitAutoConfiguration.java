package com.uhasoft.smurf.ratelimit.core.configuration;

import com.uhasoft.smurf.ratelimit.core.DefaultOriginParser;
import com.uhasoft.smurf.ratelimit.core.OriginParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class RateLimitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OriginParser<HttpServletRequest> originParser(){
        return new DefaultOriginParser();
    }
}
