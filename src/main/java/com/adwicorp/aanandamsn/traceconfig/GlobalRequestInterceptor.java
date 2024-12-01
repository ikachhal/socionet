package com.adwicorp.aanandamsn.traceconfig;

import com.adwicorp.aanandamsn.exception.BusinessException;
import com.adwicorp.aanandamsn.exception.ErrorCodes;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class TraceIdAspect {

    private static final Logger logger = LoggerFactory.getLogger(TraceIdAspect.class);

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void validateHeaders(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String traceId = request.getHeader("X-Trace-ID");
//        if (traceId == null) {
//            traceId = UUID.randomUUID().toString();
//        }
//        TraceContext.setTraceId(traceId);
//        logger.info("Captured Trace ID: {}", traceId);
        MDC.put("trace_id", traceId);  // Add traceId directly to MDC for logging
        // Validate if header is missing
        if (traceId == null || traceId.isBlank()) {
            logger.error("Missing or empty X-Trace-ID header");
            throw new BusinessException(ErrorCodes.INTERNAL_SERVER_ERROR,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.INTERNAL_SERVER_ERROR));
        }
    }

    @After("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void clearTraceId() {
        TraceContext.clear();
        MDC.remove("trace_id");
    }
}
