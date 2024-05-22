package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
  // hello.aop.order 패키지와 그 하위패키지(..)를 지정하는 AspectJ 표현식
  @Pointcut("execution(* hello.aop.order..*(..))")
  public void allOrder() {
  } // pointcut 시그니처
  // 코드 내용은 비워둔다.
  // 이점 : 하나의 포인트컷 표현식을 여러 어드바이스에서 함께 사용 가능(다른 클래스에 있는 외부 어드바이스에서도 사용 가능)

  // 클래스 이름 패턴이 *Service
  @Pointcut("execution(* *..*Service.*(..))")
  public void allService() {
  }

  @Pointcut("allOrder() && allService()")
  public void orderAndService(){}
}
