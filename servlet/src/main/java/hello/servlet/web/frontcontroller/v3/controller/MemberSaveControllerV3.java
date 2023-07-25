package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.Member;
import hello.servlet.domain.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username"); // 요청 파라미터에서 username을 꺼낸다.
        int age = Integer.parseInt(paramMap.get("age")); // 요청 파라미터에서 age를 꺼낸다.

        Member member = new Member(username, age); // Member 객체를 생성한다.

        memberRepository.save(member); // 회원 저장

        ModelView mv = new ModelView("save-result"); // ModelView 객체를 생성한다.
        mv.getModel().put("member", member); // ModelView 객체에 회원 객체를 넣는다.
        return mv; // ModelView 객체를 반환한다.




    }
}
