package jpabook.jpashop.service;

import static org.junit.Assert.*;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

  @Autowired
  private MemberService memberService;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private EntityManager em;

  @Test
  public void 회원가입() {
    Member member = new Member();
    member.setName("jo");

    Long saveId = memberService.join(member);

    assertEquals(member, memberService.findOne(saveId));
  }

  @Test(expected = IllegalStateException.class)
  public void 중복_회원_예외() {
    Member member1 = new Member();
    member1.setName("jo");

    Member member2 = new Member();
    member2.setName("jo");

    memberService.join(member1);
    memberService.join(member2);

    fail("이미 존재하는 사용자입니다.");

  }
}