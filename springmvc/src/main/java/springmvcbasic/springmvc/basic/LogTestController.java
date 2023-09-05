package springmvcbasic.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class LogTestController {

    // Slf4j는 인터페이스이다.
    // LogBack은 Slf4j를 구현한 라이브러리이다.

//    private final Logger log = LoggerFactory.getLogger(getClass());// getClass()는 현재 클래스를 의미한다.

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        System.out.println("name = " + name);

        // 단계별로 정리
        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

//        log.trace("trace log = " + name); // 이렇게 하면 안된다. 이렇게 하면 name이라는 문자열을 더하는 연산이 발생한다.
        return "ok";
    }
}
