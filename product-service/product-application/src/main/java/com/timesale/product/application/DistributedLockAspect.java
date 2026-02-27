package com.timesale.product.application;

import com.timesale.product.application.port.DistributedLockPort;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@Order(1)
public class DistributedLockAspect {

    private final DistributedLockPort distributedLockPort;
    private final ExpressionParser parser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        // 1. SpEL(Spring Expression Language)을 파싱하여 동적인 Lock Key를 생성 (예: product:lock:1)
        String lockKey = parseSpel(joinPoint, distributedLock.key());

        Supplier<Object> action = () -> {
            try {
                return joinPoint.proceed();
            } catch (RuntimeException e) {
                throw (RuntimeException) e;
            } catch (Throwable e) {
                throw new RuntimeException("AOP 로직 실행 중 checked 예외 발생", e);
            }
        };

        return distributedLockPort.executeWithLock(lockKey, action);
    }

    // 메서드 파라미터(productId 등)를 읽어와서 SpEL 문자열을 실제 값으로 변환하는 로직
    private String parseSpel(ProceedingJoinPoint joinPoint, String spel) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = discoverer.getParameterNames(signature.getMethod());
        Object[] args = joinPoint.getArgs();

        StandardEvaluationContext context = new StandardEvaluationContext();
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        return parser.parseExpression(spel).getValue(context, String.class);
    }

}
