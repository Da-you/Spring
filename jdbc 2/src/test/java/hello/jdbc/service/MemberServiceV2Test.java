package hello.jdbc.service;

import static hello.jdbc.connection.ConnectionConst.PASSWORD;
import static hello.jdbc.connection.ConnectionConst.URL;
import static hello.jdbc.connection.ConnectionConst.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV2;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 트랜잭션 - 커넥션 파라미터 전달 방식 동기화
 */
@Slf4j
class MemberServiceV2Test {

  private static final String MEMBER_A = "memberA";
  private static final String MEMBER_B = "memberB";
  private static final String MEMBER_EX = "ex";

  private MemberRepositoryV2 repository;
  private MemberServiceV2 memberServiceV2;

  @BeforeEach
  void beforeEach() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    repository = new MemberRepositoryV2(dataSource);
    memberServiceV2 = new MemberServiceV2(dataSource, repository);
  }

  @AfterEach
  void afterEach() throws SQLException {
    repository.delete(MEMBER_A);
    repository.delete(MEMBER_B);
    repository.delete(MEMBER_EX);
  }

  @Test
  @DisplayName("정상 이체")
  void accountTransfer() throws SQLException {
    Member memberA = new Member(MEMBER_A, 10000);
    Member memberB = new Member(MEMBER_B, 10000);
    repository.save(memberA);
    repository.save(memberB);
    log.info("start TX");
    memberServiceV2.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);
    log.info("end TX");
    Member findMemberA = repository.findById(memberA.getMemberId());
    Member findMemberB = repository.findById(memberB.getMemberId());
    assertThat(findMemberA.getMoney()).isEqualTo(8000);
    assertThat(findMemberB.getMoney()).isEqualTo(12000);

  }

  @Test
  @DisplayName("이체중 예외 발생")
  void accountTransferEx() throws SQLException {
    Member memberA = new Member(MEMBER_A, 10000);
    Member memberEx = new Member(MEMBER_EX, 10000);
    repository.save(memberA);
    repository.save(memberEx);

    assertThatThrownBy(
        () -> memberServiceV2.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
        .isInstanceOf(IllegalStateException.class);

    Member findMemberA = repository.findById(memberA.getMemberId());
    Member findMemberB = repository.findById(memberEx.getMemberId());
    assertThat(findMemberA.getMoney()).isEqualTo(10000);
    assertThat(findMemberB.getMoney()).isEqualTo(10000);

  }


}