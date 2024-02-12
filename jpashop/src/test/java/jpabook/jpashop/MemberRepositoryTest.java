//package jpabook.jpashop;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.Assert.*;
//
//import jpabook.jpashop.domain.Member;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class MemberRepositoryTest {
//
//  @Autowired
//  MemberRepository memberRepository;
//
//  /**
//   * @Transactional 없는 상태에서 실행시
//   * org.springframework.dao.InvalidDataAccessApiUsageException: No EntityManager with actual transaction available for current thread -
//   * cannot reliably process 'persist' call 발생
//   * 또한 테스트 케이스에 있는경우  실행후 바로 롤백실행
//   */
//  @Test
//  @Transactional
//  @Rollback(value = false) // @Transactional로 인한 롤백 거부
//  public void testMember() {
//    Member member = new Member();
//    member.setUsername("memberA");
//
//    Long saveId = memberRepository.save(member);
//    Member findMembmer = memberRepository.find(saveId);
//
//    assertThat(findMembmer.getId()).isEqualTo(saveId);
//    assertThat(findMembmer.getUsername()).isEqualTo(member.getUsername());
//    // 같은 영속성 컨텍스트 안에서 pk값이 같으면 같은 값으로 판단
//    assertThat(findMembmer).isEqualTo(member);
//    System.out.println("findMember == member :"+ (findMembmer == member));
//  }
//
//
//
//
//
//
//}