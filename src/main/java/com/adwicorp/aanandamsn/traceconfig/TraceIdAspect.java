package com.adwicorp.aanandamsn.traceconfig;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Aspect
@Component
public class TraceIdAspect {

    private static final Logger logger = LoggerFactory.getLogger(TraceIdAspect.class);

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void captureTraceId(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String traceId = request.getHeader("X-Trace-ID");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString();
        }
        TraceContext.setTraceId(traceId);
        logger.info("Captured Trace ID: {}", traceId);
    }

    @After("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void clearTraceId() {
        TraceContext.clear();
    }
}
