package mx.com.tecnetia.orthogonal.aop.aspectj;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Aspect
@RequiredArgsConstructor
public class AllControllersAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerMethods() {
    }

    @Around("restControllerMethods()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        long initialTime = System.nanoTime();
        var uri = request.getRequestURL().toString();
        String methodName = joinPoint.getSignature().getName();
        log.info("{} {}. Inicio", methodName, request.getRequestURL().toString());
        Object returnedObject = joinPoint.proceed();
        log.info("{} {}. Fin", methodName, request.getRequestURL().toString());
        long endTime = System.nanoTime();
        log.info("La ejecución de {} llevó {} ms", uri, TimeUnit.NANOSECONDS.toMillis(endTime - initialTime));
        return returnedObject;
    }
}
