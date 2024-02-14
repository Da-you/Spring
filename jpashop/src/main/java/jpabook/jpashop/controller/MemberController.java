package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import java.util.List;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * JPA를 사용할떄는 엔티티는 핵심 비즈니스 로직을 제외화고는 순수한 상태로 가져가야한. DTO사용 권장 Api를 생성시에는 절대 엔티티를 웹으로 노출하면 안된다
 */
@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/members/new")
  public String createForm(Model model) {
    model.addAttribute("memberForm", new MemberForm());
    // 모델의 역할 컨트롤러에서 뷰로 이동할떄 데이터를 실어 나른다.
    return "members/createMemberForm";
  }

  @PostMapping("/members/new")
  public String create(@Valid MemberForm form, BindingResult result) {

    if (result.hasErrors()) {
      return "members/createMemberForm";
    }
    Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

    Member member = new Member();
    member.setName(form.getName());
    member.setAddress(address);

    memberService.join(member);
    return "redirect:/";
  }

  @GetMapping("/members")
  public String memberList(Model model) {
    List<Member> members = memberService.members();
    model.addAttribute("members", members);
    return "members/memberList";

  }

}
