package hello.jdbc.service;

import static hello.jdbc.connection.ConnectionConst.PASSWORD;
import static hello.jdbc.connection.ConnectionConst.URL;
import static hello.jdbc.connection.ConnectionConst.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 트랜잭션 -  @@Transactional AOP
 */
@Slf4j
@SpringBootTest
class MemberServiceV3_3Test {

  private static final String MEMBER_A = "memberA";
  private static final String MEMBER_B = "memberB";
  private static final String MEMBER_EX = "ex";
  @Autowired
  private MemberRepositoryV3 repository;
  @Autowired
  private MemberServiceV3_3 memberService;

  @TestConfiguration
  static class TestConfig {

    @Bean
    DataSource dataSource() {
      return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    }

    @Bean
    PlatformTransactionManager transactionManager() {
      return new DataSourceTransactionManager(dataSource( ));
    }
    @Bean
    MemberRepositoryV3 repository(){
      return new MemberRepositoryV3(dataSource());
    }
    @Bean
    MemberServiceV3_3 memberService(){
      return new MemberServiceV3_3(repository());
    }
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
    memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);
    log.info("end TX");
    Member findMemberA = repository.findById(memberA.getMemberId());
    Member findMemberB = repository.findById(memberB.getMemberId());
    assertThat(findMemberA.getMoney()).isEqualTo(8000);
    assertThat(findMemberB.getMoney()).isEqualTo(12000);

  }
  @Test
  void AopCheck(){
    log.info("memberService class={}",memberService.getClass());
    log.info("memberRepository class={}",repository.getClass());
    Assertions.assertThat(AopUtils.isAopProxy(memberService)).isTrue();
    Assertions.assertThat(AopUtils.isAopProxy(repository)).isFalse();

  }

  @Test
  @DisplayName("이체중 예외 발생")
  void accountTransferEx() throws SQLException {
    Member memberA = new Member(MEMBER_A, 10000);
    Member memberEx = new Member(MEMBER_EX, 10000);
    repository.save(memberA);
    repository.save(memberEx);

    assertThatThrownBy(
        () -> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
        .isInstanceOf(IllegalStateException.class);

    Member findMemberA = repository.findById(memberA.getMemberId());
    Member findMemberB = repository.findById(memberEx.getMemberId());
    assertThat(findMemberA.getMoney()).isEqualTo(10000);
    assertThat(findMemberB.getMoney()).isEqualTo(10000);

  }


}