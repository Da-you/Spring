package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV2;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 트랜잭션 -  파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

  private final DataSource dataSource;
  private final MemberRepositoryV2 repository;

  public void accountTransfer(String fromId, String toId, int money) throws SQLException {
    Connection con = dataSource.getConnection();
    try {
      con.setAutoCommit(false); // 트랜잭션 시작
      // 비즈니스 로직
      bizLogic(fromId, toId, money, con);
      con.commit(); // 성공시 커밋
    } catch (Exception e) {
      con.rollback(); // 실패시 롤백
      throw new IllegalStateException(e);
    } finally {
      release(con);
    }
  }

  private void bizLogic(String fromId, String toId, int money, Connection con)
      throws SQLException {
    Member fromMember = repository.findById(con, fromId);
    Member toMember = repository.findById(con, toId);

    repository.update(con, fromId, fromMember.getMoney() - money);
    validation(toMember);
    repository.update(con, toId, toMember.getMoney() + money);
  }

  private static void release(Connection con) {
    if (con != null) {
      try {
        con.setAutoCommit(true); // 커넥션 풀 고려
        con.close();
      } catch (Exception e) {
        log.info("error", e);
      }
    }
  }

  private static void validation(Member toMember) {
    if (toMember.getMemberId().equals("ex")) {
      throw new IllegalStateException("이체중 예외 발생");
    }
  }

}
