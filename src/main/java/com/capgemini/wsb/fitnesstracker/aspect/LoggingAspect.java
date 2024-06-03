package com.capgemini.wsb.fitnesstracker.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Log method entry.
     *
     * @param joinPoint join point for advice
     */
    @Before("execution(* com.capgemini.wsb.fitnesstracker..*Service.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Start method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    /**
     * Log method exit.
     *
     * @param joinPoint join point for advice
     * @param result the result of the method execution
     */
    @AfterReturning(pointcut = "execution(* com.capgemini.wsb.fitnesstracker..*Service.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("End method: {} with result: {}", joinPoint.getSignature(), result);
    }
}
