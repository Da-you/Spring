package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  @Transactional
  public Long join(Member member) {
    validDuplicateMember(member);
    memberRepository.save(member);
    return member.getId();
  }

  private void validDuplicateMember(Member member) {
    List<Member> members = memberRepository.findByName(member.getName());
    if (!members.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 사용자입니다.");
    }
  }

  public List<Member> members() {
    return memberRepository.findAll();
  }

  public Member findOne(Long memberId) {
    return memberRepository.find(memberId);
  }
}
