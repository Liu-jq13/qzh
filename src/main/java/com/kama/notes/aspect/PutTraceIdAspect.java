package com.kama.notes.aspect;

import java.util.UUID;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PutTraceIdAspect {
    private static final String TRACE_ID_KEY = "traceId";
    /**
     * 切面切入点，拦截所有控制器的方法。
     */
    @Before("execution(* com.kama.notes..*(..))")
    public void addTraceIdToLog() {
        // 如果当前 MDC 中没有 traceId，则生成一个新的
        if (MDC.get(TRACE_ID_KEY) == null) {
            String traceId = UUID.randomUUID().toString();
            MDC.put(TRACE_ID_KEY, traceId);
        }
    }
}
