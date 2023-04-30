package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("hello") // Get 방식으로 hello라는 url이 들어오면 이 메소드를 호출
    public String hello(Model model) {
        model.addAttribute("data", "hello!!"); // key: data, value: hello!!
        return "hello"; // resources/templates/hello.html을 찾아서 렌더링
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        // @RequestParam: url에 파라미터로 넘어온 값을 name에 저장
        model.addAttribute("name", name);
        return "hello-template";
    }
}
