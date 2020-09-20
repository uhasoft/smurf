package com.uhasoft.smurf.core.context;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class RequestContext {

    private static ThreadLocal<Map<String, String>> holder = ThreadLocal.withInitial(HashMap::new);

    public static void set(){
        Map<String, String> map = new HashMap<>();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null){
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> names = request.getHeaderNames();
            while(names.hasMoreElements()){
                String header = names.nextElement();
                map.put(header, request.getHeader(header));
            }
        }
        holder.set(map);
    }

    public static Map<String, String> getHeaders(){
        return holder.get();
    }


}
