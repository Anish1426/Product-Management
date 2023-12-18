package com.product.product_management.Logger;


import com.product.product_management.Controller.Controller;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
@Document
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }

    @Around("execution(* com.product.product_management.Repository.*.*(..))")
    public Object logAroundRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before executing MongoDB query at " + formatLocalDateTime(LocalDateTime.now()));

        Object result = null;

        try {
            result = joinPoint.proceed();
            logger.info("After executing MongoDB query at " + formatLocalDateTime(LocalDateTime.now()));
        } catch (Exception e) {
            logger.error("Exception after executing MongoDB query at " + formatLocalDateTime(LocalDateTime.now()), e);
            throw e;
        }

        return result;
    }
    @Around("execution(* com.product.product_management.Controller.*.*(..))")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before executing API method at " + formatLocalDateTime(LocalDateTime.now()));

        Object result = null;

        try {
            result = joinPoint.proceed();
            logger.info("After executing API method at " + formatLocalDateTime(LocalDateTime.now()));
        } catch (Exception e) {
            logger.error("Exception after executing API method at " + formatLocalDateTime(LocalDateTime.now()), e);
            throw e;
        }

        return result;
    }



}
