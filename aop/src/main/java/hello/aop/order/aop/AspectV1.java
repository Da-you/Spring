package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect // 어드바이스와 포인트컷을 모듈화한것
public class AspectV1 {

  @Around("execution(* hello.aop.order..*(..))") // 포인트 컷
  // "execution(* hello.aop.order..*(..))" 은 패키지와 그 하위패키지(..)를 지정하는 AspectJ 표현식
  public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ // 어드바이스
    log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
    return joinPoint.proceed();
  }

}
