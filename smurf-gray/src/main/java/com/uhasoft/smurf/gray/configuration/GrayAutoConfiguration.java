package com.uhasoft.smurf.gray.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
@RibbonClients(defaultConfiguration = GrayRibbonAutoConfiguration.class)
public class GrayAutoConfiguration {

}
