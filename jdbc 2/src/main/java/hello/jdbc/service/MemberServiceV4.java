package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 트랜잭션 -  @@Transactional AOP
 */
@Slf4j
public class MemberServiceV4 {

  private final MemberRepository repository;

  public MemberServiceV4(MemberRepository repository) {
    this.repository = repository;
  }
@Transactional
  public void accountTransfer(String fromId, String toId, int money)  {
    bizLogic(fromId, toId, money);
  }

  private void bizLogic(String fromId, String toId, int money)
        {
    Member fromMember = repository.findById(fromId);
    Member toMember = repository.findById(toId);

    repository.update(fromId, fromMember.getMoney() - money);
    validation(toMember);
    repository.update(toId, toMember.getMoney() + money);
  }

  private static void validation(Member toMember) {
    if (toMember.getMemberId().equals("ex")) {
      throw new IllegalStateException("이체중 예외 발생");
    }
  }

}
