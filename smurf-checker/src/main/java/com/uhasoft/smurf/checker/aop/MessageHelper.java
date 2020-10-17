package com.uhasoft.smurf.checker.aop;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class MessageHelper {

    private MessageSource messageSource;

    public MessageHelper(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    public String get(String key, String... params){
        return messageSource.getMessage(key, params, Locale.ENGLISH);
    }


}
