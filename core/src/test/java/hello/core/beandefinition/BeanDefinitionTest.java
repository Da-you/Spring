package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
// BeanDefinition은 스프링이 내부에서 사용하는 설정 메타 정보이다.
    @Test
    @DisplayName("빈 설정 메타 정보 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames(); // 스프링에 등록된 모든 빈 이름을 조회한다.
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition definition = ac.getBeanDefinition(beanDefinitionName); // 빈 이름으로 빈 객체(인스턴스)를 조회한다.

            if (definition.getRole() == BeanDefinition.ROLE_APPLICATION) { // ROLE_APPLICATION 은 직접 등록한 애플리케이션 빈이다.
                System.out.println("name = " + beanDefinitionName + " definition = " + definition);
            }
        }
    }
}
