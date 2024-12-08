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
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class GlobalRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GlobalRequestInterceptor.class);

    List<String> skipValidationPaths = Arrays.asList("/swagger-ui", "/v3/api-docs");
    List<String> requiredHeaders = Arrays.asList("trace-id", "request-date-time");

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void validateHeaders(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // Skip validation for Swagger UI and API Docs
        String requestURI = request.getRequestURI();
        for (String path : skipValidationPaths) {
            if (requestURI.contains(path)) {
                return; // Skip validation
            }
        }
        for (String header : requiredHeaders) {
            String headerValue = request.getHeader(header);
            if (headerValue == null || headerValue.isBlank()) {
                logger.error("Missing or empty {} header", header);
                throw new BusinessException(
                        ErrorCodes.PARAM_MISSING,
                        ErrorCodes.ERROR_CODE_MESSAGE_MAP.get(ErrorCodes.PARAM_MISSING) + header
                );
            }
        }
        MDC.put("trace_id", request.getHeader("trace-id"));
    }

    @After("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void clearTraceId() {
        MDC.remove("trace_id");
    }
}
