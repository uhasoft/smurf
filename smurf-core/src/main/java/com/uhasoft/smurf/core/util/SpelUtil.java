package com.uhasoft.smurf.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class SpelUtil {

    private static final Logger logger = LoggerFactory.getLogger(SpelUtil.class);

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    public static boolean eval(String expression, StandardEvaluationContext context){
        Boolean value = PARSER.parseExpression(expression).getValue(context, Boolean.class);
        logger.debug("Evaluating expression: [{}], result: {}", expression, value);
        return value != null && value;
    }
}
