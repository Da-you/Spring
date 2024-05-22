package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect // 어드바이스와 포인트컷을 모듈화한것
public class AspectV3 {

  // hello.aop.order 패키지와 그 하위패키지(..)를 지정하는 AspectJ 표현식
  @Pointcut("execution(* hello.aop.order..*(..))")
  private void allOrder() {
  } // pointcut 시그니처
  // 코드 내용은 비워둔다.
  // 이점 : 하나의 포인트컷 표현식을 여러 어드바이스에서 함께 사용 가능(다른 클래스에 있는 외부 어드바이스에서도 사용 가능)

  // 클래스 이름 패턴이 *Service
  @Pointcut("execution(* *..*Service.*(..))")
  private void allService() {
  }

  @Around("allOrder()") // 포인트 컷
  public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable { // 어드바이스
    log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
    return joinPoint.proceed();
  }

  // hello.aop.order 패키지와 그 하위패키지(..) 이면서 클래스 이름 패턴이 *Service
  @Around("allOrder() && allService()")
  public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
      Object result = joinPoint.proceed();
      log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
      return result;
    } catch (Exception e) {
      log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
      throw e;
    } finally {
      log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
    }

  }

}
