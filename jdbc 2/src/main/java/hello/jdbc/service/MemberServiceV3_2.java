package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 트랜잭션 -  트랜잭션 템플릿 사용
 */
@Slf4j
//@RequiredArgsConstructor
public class MemberServiceV3_2 {

  //  private final DataSource dataSource;
//  private final PlatformTransactionManager transactionManager;
  private final TransactionTemplate txTemplate;
  private final MemberRepositoryV3 repository;

  public MemberServiceV3_2(PlatformTransactionManager transactionManager,
      MemberRepositoryV3 repository) {
    this.txTemplate = new TransactionTemplate(transactionManager);
    this.repository = repository;
  }

  public void accountTransfer(String fromId, String toId, int money) throws SQLException {

    txTemplate.executeWithoutResult((status) -> { // 트랜잭션을 시작하고 정상작동시 커밋 실패시 롤백
          try {
            bizLogic(fromId, toId, money);
          } catch (SQLException e) {
            throw new IllegalStateException(e);
          }
        }
    );
  }

  private void bizLogic(String fromId, String toId, int money)
      throws SQLException {
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
