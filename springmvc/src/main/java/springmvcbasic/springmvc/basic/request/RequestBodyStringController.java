package springmvcbasic.springmvc.basic.request;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        // inputStream은 byte 코드를 읽기 때문에 문자로 변환해야 함
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // 바이트 코드를 문자로 변환, 인코딩 정보를 알려줘야 함
        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // 바이트 코드를 문자로 변환, 인코딩 정보를 알려줘야 함
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String body = httpEntity.getBody();
        log.info("messageBody={}", body);
        return new HttpEntity<>("ok");
    }
    @ResponseBody
    @PostMapping("/request-body-string-vv")
    public String requestBodyStringV3(@RequestBody String messageBody){
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
