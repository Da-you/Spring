package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest  {


    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeTest.class);
        System.out.println("find prototypeBean1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        Assertions.assertThat(bean1).isNotSameAs(bean2);

        ac.close(); // close()를 호출해도 @PreDestroy가 호출되지 않는다.
        //bean1.destroy(); // 직접 호출해야 한다.



    }
    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        private void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        private void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
