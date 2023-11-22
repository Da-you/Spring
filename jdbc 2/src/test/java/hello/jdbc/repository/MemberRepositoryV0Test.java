package hello.jdbc.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import hello.jdbc.domain.Member;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
@Slf4j
class MemberRepositoryV0Test {

  MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();
  @Test
  void crud() throws SQLException {
    // save
    Member member = new Member("member4",10000);
    repositoryV0.save(member);

    // find by id
    Member findMember = repositoryV0.findById(member.getMemberId());
    log.info("find member =" + findMember);
    assertThat(member).isEqualTo(findMember);
    // update
    repositoryV0.update(member.getMemberId(), 20000);
    Member updateMember = repositoryV0.findById(member.getMemberId());
    assertThat(updateMember.getMoney()).isEqualTo(20000);

    // delete
    repositoryV0.delete(member.getMemberId());
    assertThatThrownBy(() -> repositoryV0.findById(member.getMemberId()))
        .isInstanceOf( NoSuchElementException.class);
  }
}