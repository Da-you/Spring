package hello.jdbc.service;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 기본 동작, 트랜잭션이 없어서 문제 발생
 */
class MemberServiceV1Test {
  private static final String MEMBER_A = "memberA";
  private static final String MEMBER_B = "memberB";
  private static final String MEMBER_EX = "ex";

  private MemberRepositoryV1 repository;
  private MemberServiceV1 memberServiceV1;
  @BeforeEach
  void beforeEach(){
    DriverManagerDataSource dataSource = new DriverManagerDataSource(URL,USERNAME,PASSWORD);
    repository = new MemberRepositoryV1(dataSource);
    memberServiceV1 = new MemberServiceV1(repository);
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

    memberServiceV1.accountTransfer(memberA.getMemberId(),memberB.getMemberId(),2000);

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

assertThatThrownBy(()-> memberServiceV1.accountTransfer(memberA.getMemberId(),memberEx.getMemberId(),2000))
    .isInstanceOf(IllegalStateException.class);

    Member findMemberA = repository.findById(memberA.getMemberId());
    Member findMemberB = repository.findById(memberEx.getMemberId());
    assertThat(findMemberA.getMoney()).isEqualTo(8000);
    assertThat(findMemberB.getMoney()).isEqualTo(10000);

  }


}