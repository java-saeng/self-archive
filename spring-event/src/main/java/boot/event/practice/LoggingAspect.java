package boot.event.practice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Before("execution(* boot.event.practice..*.*(..))")
  public void logging(JoinPoint joinPoint) {
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();
    long threadId = Thread.currentThread().getId();
    String threadName = Thread.currentThread().getName();

    log.info("Executing method [Class: {}, Method: {}] "
            + "Thread [ID: {}, Name: {}]",
        className, methodName, threadId, threadName);
  }
}
