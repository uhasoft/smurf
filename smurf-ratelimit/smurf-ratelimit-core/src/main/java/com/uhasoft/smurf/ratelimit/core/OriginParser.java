package com.uhasoft.smurf.ratelimit.core;

/**
 * @author Weihua
 * @since 1.0.0
 */
public interface OriginParser<T> {

    /**
     * T mostly could be a HttpServletRequest
     * @param t the object from which the origin is
     * @return The Origin
     */
    String parse(T t);
}
