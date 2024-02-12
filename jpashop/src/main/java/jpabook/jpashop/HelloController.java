package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  @GetMapping("hello")
  public String hello(Model model){
    model.addAttribute("data","hello!!");
    return "hello"; // 화면 이름, 스프링 부트의 타임리프가 자동으로 hello.html로 매핑
  }

}
