package ${basePackage}.configuration;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
@MapperScan(basePackages = "${basePackage}.dao")
public class DaoAutoConfiguration {

}
