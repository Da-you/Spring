package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("hello-string")
    @ResponseBody // http의 body부에 이 데이터를 직접 넣어주겠다는 의미
    public String helloString(@RequestParam("name") String name){
        return " hello " + name; // hello + name을 렌더링
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello(); // 객체 생성
        hello.setName(name); // name을 넣어줌
        return hello; // 객체를 반환하면 json 형태로 반환됨
    }

    static class Hello {
        private String name; // key: name, value: name

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
