package ${basePackage}.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
@ComponentScan(basePackages = "${basePackage}.service")
public class ServiceAutoConfiguration {

}
