package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService memberService = ac.getBean("memberService", MemberService.class); // 빈 이름으로 빈 객체(인스턴스)를 조회한다.
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class); // memberService 가 MemberServiceImpl 의 인스턴스인지 확인한다.
    }
    @Test
    @DisplayName("이름 없이 빈 타입으로만 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class); // 빈 이름으로 빈 객체(인스턴스)를 조회한다.
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class); // memberService 가 MemberServiceImpl 의 인스턴스인지 확인한다.
    }
    // 구현에 의존적인 코드로 조회시 유연성 저하로 권장되지 않는 코드
    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2(){
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class); // 빈 이름으로 빈 객체(인스턴스)를 조회한다.
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class); // memberService 가 MemberServiceImpl 의 인스턴스인지 확인한다.
    }

    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX(){
//        ac.getBean("XXXX",MemberService.class);
//        MemberService xxxx = ac.getBean("xxxx", MemberService.class); // 빈 이름으로 빈 객체(인스턴스)를 조회한다.
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxx", MemberService.class)); // memberService 가 MemberServiceImpl 의 인스턴스인지 확인한다.
    }

}
