package com.uhasoft.smurf.ratelimit.guava.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.uhasoft.smurf.common.annotation.Resource;
import com.uhasoft.smurf.common.util.StringUtils;
import com.uhasoft.smurf.ratelimit.core.exception.RateLimitException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Aspect
public class ResourceAop {

    private static Map<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

    @Around("@annotation(com.uhasoft.smurf.common.annotation.Resource)")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Resource resource = method.getDeclaredAnnotation(Resource.class);
        if(StringUtils.hasText(resource.value()) && resource.threshold() > 0){
            RateLimiter rateLimiter = rateLimiters.get(resource.value());
            if(rateLimiter == null){
                rateLimiter = RateLimiter.create(resource.threshold());
                rateLimiters.put(resource.value(), rateLimiter);
            }
            if(rateLimiter.tryAcquire()){
                return joinPoint.proceed();
            }
            throw new RateLimitException("Rate Limited");
        }
        return joinPoint.proceed();
    }
}
