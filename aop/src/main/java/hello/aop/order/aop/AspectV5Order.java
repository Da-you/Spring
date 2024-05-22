package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

// 어드바이스는 클래스 단위로 순서를 변경할 수 있다.  > 클래스를 만들고 거기에 내부 클래스로 순서를 지정
@Slf4j
public class AspectV5Order {

  @Aspect
  @Order(1)
  public static class LogAspect {

    @Around("hello.aop.order.aop.Pointcuts.allOrder()") // 포인트 컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable { // 어드바이스
      log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
      return joinPoint.proceed();
    }
  }

  @Aspect
  @Order(2)
  public static class TxAspect {

    // hello.aop.order 패키지와 그 하위패키지(..) 이면서 클래스 이름 패턴이 *Service
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
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

}
