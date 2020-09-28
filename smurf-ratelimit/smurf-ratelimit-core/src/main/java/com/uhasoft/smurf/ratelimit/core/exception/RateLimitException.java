package com.uhasoft.smurf.ratelimit.core.exception;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class RateLimitException extends RuntimeException {

    public RateLimitException(String message){
        super(message);
    }
}
