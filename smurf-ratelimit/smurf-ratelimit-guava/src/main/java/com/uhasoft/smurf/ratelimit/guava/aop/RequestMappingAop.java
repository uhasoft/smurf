package com.uhasoft.smurf.ratelimit.guava.aop;

import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.Gson;
import com.uhasoft.registry.core.RegistryServer;
import com.uhasoft.smurf.common.util.StringUtils;
import com.uhasoft.smurf.config.core.ConfigService;
import com.uhasoft.smurf.ratelimit.core.OriginParser;
import com.uhasoft.smurf.ratelimit.core.exception.RateLimitException;
import com.uhasoft.smurf.ratelimit.core.model.Rule;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.uhasoft.smurf.ratelimit.core.constant.RateLimitConstant.DEFAULT_ORIGIN;
import static org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Aspect
public class RequestMappingAop {

    private static final Logger logger = LoggerFactory.getLogger(RequestMappingAop.class);

    private static final Map<String, Map<String, RateLimiter>> rateLimiters = new ConcurrentHashMap<>();

    @Autowired
    private ConfigService configService;

    @Autowired
    private RegistryServer registryServer;

    @Autowired
    private OriginParser<HttpServletRequest> originParser;

    @PostConstruct
    public void init(){
        configService.observe("smurf.ratelimit." + registryServer.getServiceName(), "smurf.ratelimit.rules", value -> {
            logger.info("RateLimit rules: {}", value);
            if(value != null){
                Type type = new TypeToken<List<Rule>>(){}.getType();
                List<Rule> rules = new Gson().fromJson(value, type);
                Map<String, Map<String, RateLimiter>> newRateLimiters = new ConcurrentHashMap<>();
                for(Rule rule : rules){
                    Map<String, RateLimiter> originRateLimiters = new ConcurrentHashMap<>();
                    if(rule.getDefaultThreshold() > 0){
                        originRateLimiters.put(DEFAULT_ORIGIN, RateLimiter.create(rule.getDefaultThreshold()));
                    }
                    if(!CollectionUtils.isEmpty(rule.getOriginThreshold())){
                        for(String origin : rule.getOriginThreshold().keySet()){
                            originRateLimiters.put(origin, RateLimiter.create(rule.getOriginThreshold().get(origin)));
                        }
                    }
                    newRateLimiters.put(rule.getResource(), originRateLimiters);
                }
                synchronized (rateLimiters){
                    rateLimiters.clear();
                    rateLimiters.putAll(newRateLimiters);
                }
                logger.info("Loaded ratelimit rules for {} APIs.", rules.size());
            }
        });
    }

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null){
            HttpServletRequest request = attributes.getRequest();
            String url = (String)request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
            if(StringUtils.hasText(url)){
                String origin = originParser.parse(request);
                String method = request.getMethod();
                RateLimiter rateLimiter = rateLimiters.get(method + ":" +url).get(origin);
                if(rateLimiter != null){
                    if(rateLimiter.tryAcquire()){
                        return joinPoint.proceed();
                    }
                    throw new RateLimitException("Rate Limited");
                }
            }
        }
        return joinPoint.proceed();
    }
}
