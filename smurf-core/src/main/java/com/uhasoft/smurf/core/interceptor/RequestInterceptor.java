package com.uhasoft.smurf.core.interceptor;

import com.uhasoft.smurf.core.context.RequestContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class RequestInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestContext.set();
        return true;
    }

}
