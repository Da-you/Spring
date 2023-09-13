package springmvcbasic.springmvc.basic.response;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import springmvcbasic.springmvc.basic.HelloData;

import java.io.IOException;

/**
 * @ResponseBody는 클래스 레벨에서 설정가능하며 설정시 클래스에있는 모든 api에 적용됨
 * @Controller + @ResponseBody = @RestController
 */
@Slf4j
@Controller
public class ResponseBodyController {


    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        // HttpEntity와 이를 상속한  ResponseEntity 는 응답 코드를 설정할 수 있음
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody // 뷰렌더링 없이 HTTP 메시지 바디에 직접 데이터를 입력
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        // HttpEntity와 이를 상속한  ResponseEntity 는 응답 코드를 설정할 수 있음
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-json-v2")
    @ResponseStatus(value = HttpStatus.OK) // 상태코드를 설정할 수 있음
    public HelloData responseBodyJsonV2() {
        // HttpEntity와 이를 상속한  ResponseEntity 는 응답 코드를 설정할 수 있음
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }
}
