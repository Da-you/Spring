package hello.jdbc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import hello.jdbc.repository.MemberRepositoryV3;
import hello.jdbc.repository.MemberRepositoryV4_1;
import hello.jdbc.repository.MemberRepositoryV4_2;
import hello.jdbc.repository.MemberRepositoryV5;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 트랜잭션 -  스프링 부트 자동 리소스  등록
 */
@Slf4j
@SpringBootTest
class MemberServiceV4Test {

  private static final String MEMBER_A = "memberA";
  private static final String MEMBER_B = "memberB";
  private static final String MEMBER_EX = "ex";
  @Autowired
  private MemberRepository repository;
  @Autowired
  private MemberServiceV4 memberService;


  @TestConfiguration
  static class TestConfig {
    private final DataSource dataSource;

    public TestConfig(DataSource dataSource) {
      this.dataSource = dataSource;
    }

    @Bean
    MemberRepository repository(){
//      return new MemberRepositoryV4_1(dataSource);
//      return new MemberRepositoryV4_2(dataSource);
      return new MemberRepositoryV5(dataSource);
    }
    @Bean
    MemberServiceV4 memberService(){
      return new MemberServiceV4(repository());
    }
  }

  @AfterEach
  void afterEach() {
    repository.delete(MEMBER_A);
    repository.delete(MEMBER_B);
    repository.delete(MEMBER_EX);
  }

  @Test
  @DisplayName("정상 이체")
  void accountTransfer() {
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
  void accountTransferEx()  {
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