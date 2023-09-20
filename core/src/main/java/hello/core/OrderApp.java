package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        // applicationContext.getBean 은 빈을 찾는 메서드 이며 빈 이름과 타입을 넘겨줘야 한다.
        // ApplicationContext 는 스프링 컨테이너 이며, AnnotationConfigApplicationContext 는 스프링 컨테이너를 생성하는 역할을 한다.

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP); // 1L is a long type
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        System.out.printf("order = " + order);
        System.out.printf("order.calculatePrice() = " + order.calculatePrice());


    }
}
