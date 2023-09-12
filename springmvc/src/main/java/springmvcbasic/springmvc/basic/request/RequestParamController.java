package springmvcbasic.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springmvcbasic.springmvc.basic.HelloData;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody // view 조회를 무시하고 HTTP message body에 직접 해당 내용 입력
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody // view 조회를 무시하고 HTTP message body에 직접 해당 내용 입력
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // view 조회를 무시하고 HTTP message body에 직접 해당 내용 입력
    @RequestMapping("/request-param-v4")
    // String, int, Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // view 조회를 무시하고 HTTP message body에 직접 해당 내용 입력
    @RequestMapping("/request-param-required")
    // String, int, Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
    public String requestParamRequired(@RequestParam(required = true) String username, @RequestParam(required = false) int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // view 조회를 무시하고 HTTP message body에 직접 해당 내용 입력
    @RequestMapping("/request-param-default")
    // String, int, Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
    public String requestParamDefault(@RequestParam(defaultValue = "guest") String username, @RequestParam(defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody // view 조회를 무시하고 HTTP message body에 직접 해당 내용 입력
    @RequestMapping("/request-param-map")
    // String, int, Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /**
     * @ModelAttribute 사용
     * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model 관련 부분에서 자세히 설명
     */
//    @RequestMapping("/model-attribute-v1")
//    public String modelAttributeV1(@RequestParam String username, @RequestParam int age){
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);
//        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
//        return "ok";
//    }
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * @ModelAttribute 생략 가능
     * String, int, Integer 같은 단순 타입 = @RequestParam
     * argument resolver 로 지정해둔 타입 외 = @ModelAttribute
     */
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
