package com.uhasoft.smurf.checker.configuration;

import com.uhasoft.smurf.checker.aop.MessageHelper;
import com.uhasoft.smurf.checker.aop.CheckerInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class CheckerAutoConfiguration {

    @Bean
    public MessageHelper messageHelper(MessageSource messageSource){
        return new MessageHelper(messageSource);
    }

    @Bean
    public CheckerInterceptor validatorInterceptor(){
        return new CheckerInterceptor();
    }

}
