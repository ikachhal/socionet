package com.adwicorp.aanandamsn.configuration;

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
public class GlobalRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GlobalRequestInterceptor.class);

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void validateHeaders(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // Skip validation for Swagger UI and API Docs
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/swagger-ui") || requestURI.contains("/v3/api-docs")) {
            return; // Skip validation
        }
        String traceId = request.getHeader("X-Trace-ID");
        // Validate if header is missing
        if (traceId == null || traceId.isBlank()) {
            logger.error("Missing or empty X-Trace-ID header");
            throw new BusinessException(ErrorCodes.PARAM_MISSING,
                    ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.PARAM_MISSING) + "X-Trace-ID");
        }
        MDC.put("trace_id", traceId);
    }

    @After("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void clearTraceId() {
        MDC.remove("trace_id");
    }
}
