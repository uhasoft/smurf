package com.uhasoft.smurf.checker.aop;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uhasoft.smurf.common.util.SpelUtil;
import com.uhasoft.smurf.common.util.StringUtils;
import com.uhasoft.smurf.checker.annotation.Checked;
import org.apache.commons.io.FileUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * @author Weihua
 * @since 1.0.0
 */
@Aspect
public class CheckerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CheckerInterceptor.class);

    /**
     * Make it AtomicReference in case it will be updated in some day.
     */
    private static final AtomicReference<Map<String, List<CheckRule>>> RULES = new AtomicReference<>();

    private static final String DEFAULT_RULE_FILE = "classpath:smurf/checker-rules.json";

    private static final String DEFAULT_CHECKER_FILE = "classpath:smurf/checker.json";

    /**
     * r means record
     */
    private static final String BEAN_VARIABLE_NAME = "r";

    @Autowired
    private MessageHelper messageHelper;

    /**
     * Load the rules and checkers from the json file
     */
    @PostConstruct
    public void init() throws IOException {
        try {
            //Load the rules
            File rulesFile = ResourceUtils.getFile(DEFAULT_RULE_FILE);
            String rulesJson = FileUtils.readFileToString(rulesFile);
            Type ruleType = new TypeToken<List<CheckRule>>(){}.getType();
            List<CheckRule> rules = new Gson().fromJson(rulesJson, ruleType);

            //Load the checkers
            File checkersFile = ResourceUtils.getFile(DEFAULT_CHECKER_FILE);
            String checkersJson = FileUtils.readFileToString(checkersFile);
            Type checkerType = new TypeToken<Map<String, List<String>>>(){}.getType();
            Map<String, List<String>> checkers = new Gson().fromJson(checkersJson, checkerType);

            //Find the rules for each checker and save them to the map
            Map<String, List<CheckRule>> checkerRules = new HashMap<>();
            for(String checkerName : checkers.keySet()){
                Predicate<CheckRule> predicate = r -> checkers.get(checkerName).contains(r.getId());
                checkerRules.put(checkerName, rules.stream().filter(predicate).collect(Collectors.toList()));
            }

            RULES.set(checkerRules);
        } catch (FileNotFoundException ex){
            logger.warn("Checker rule file missing: {}", ex.getMessage());
            logger.warn("You enabled smurf checker, but no rules was specified. " +
                    "if you really don't need checker, please disable it by setting smurf.checker.enabled=false.");
        }

    }

    /**
     * Intercept any method whose parameter annotated with @Checked
     * .. indicates I do not mind if the method has more parameters
     */
    @Pointcut("execution(* *(.., @com.uhasoft.smurf.checker.annotation.Checked (*) ,..))")
    public void methodsWithAnnotatedParameter() {
    }

    @Before(value = "methodsWithAnnotatedParameter()")
    public void intercept(JoinPoint joinPoint) {
        if(joinPoint.getSignature() instanceof MethodSignature){
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            Annotation[][] annotations = signature.getMethod().getParameterAnnotations();
            for(int i = 0; i < annotations.length; i++){
                for(int j = 0; j < annotations[i].length; j++){
                    if(annotations[i][j] instanceof Checked){
                        Checked checked = (Checked)annotations[i][j];
                        String value = checked.value();
                        List<CheckRule> rules;
                        if(StringUtils.hasText(value)){
                            rules = RULES.get().get(value);
                        } else {
                            String className = joinPoint.getTarget().getClass().getSimpleName();
                            String methodName = signature.getName();
                            rules = RULES.get().get(className + "." + methodName);
                        }
                        if(!CollectionUtils.isEmpty(rules)){
                            check(joinPoint.getArgs()[i], rules);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void check(Object object, List<CheckRule> rules){
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable(BEAN_VARIABLE_NAME, object);
        for(CheckRule rule : rules){
            if(SpelUtil.eval(rule.getPrerequisite(), context)
                && !SpelUtil.eval(rule.getCondition(), context)){
                String message = messageHelper.get(rule.getMessageId(), rule.getMessageArgs());
                throw new CheckerException(message);
            }
        }

    }
}
