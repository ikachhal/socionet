package com.adwicorp.aanandamsn.traceconfig;

import org.springframework.stereotype.Component;

@Component
public class TraceContext {
    private static final ThreadLocal<String> traceId = new ThreadLocal<>();

    public static void setTraceId(String traceIdValue) {
        traceId.set(traceIdValue);
    }

    public static String getTraceId() {
        return traceId.get();
    }

    public static void clear() {
        traceId.remove();
    }
}
