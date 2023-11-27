package hello.jdbc.exception.basic;

import hello.jdbc.exception.basic.CheckedAppTest.NetworkClient;
import java.net.ConnectException;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
@Slf4j
public class UnCheckedAppTest {

  @Test
  void unChecked() {
    Controller controller = new Controller();

    Assertions.assertThatThrownBy(() -> controller.request())
        .isInstanceOf(RuntimeException.class);
  }

  static class Controller {

    Service service = new Service();

    public void request() throws SQLException, ConnectException {
      service.logic();
    }
  }

  static class Service {

    NetworkClient networkClient = new NetworkClient();
    Repository repository = new Repository();

    public void logic()  {
      repository.call();
      networkClient.call();
    }

  }

  @Test
  void printEx() {
    Controller controller = new Controller();
    try {
      controller.request();
    } catch (Exception e) {
//      e.printStackTrace();
      log.info("ex", e);
    }
  }
  static class NetworkClient {

    public void call() {
      throw new RuntimeConnectionException("연결 실패");
    }
  }

  static class Repository {
    public void call(){
      try {
        runSQL();
      } catch (SQLException e) {
        throw new RuntimeSQLException(e);
      }
    }

    public void runSQL() throws SQLException {
      throw new SQLException("ex");
    }
  }

  static class RuntimeConnectionException extends RuntimeException{
    public RuntimeConnectionException(String message) {
      super(message);
    }
  }

  static class RuntimeSQLException extends RuntimeException{

    public RuntimeSQLException(Throwable cause) {
      super(cause);
    }
  }

}
