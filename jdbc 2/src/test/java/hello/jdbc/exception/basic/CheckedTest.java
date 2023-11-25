package hello.jdbc.exception.basic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

@Slf4j
public class CheckedTest {

  @Test
  void checked_catch() {
    Service service = new Service();
    service.callCatch();
  }
  @Test
  void checked_throw(){
    Service service = new Service();
    assertThatThrownBy(()->
        service.callThrow()).isInstanceOf(MyCheckedException.class);
  }

  /**
   * Exception을 상속받 예외는 체크 예외가 된다.
   */
  static class MyCheckedException extends Exception {

    public MyCheckedException(String message) {
      super(message);
    }
  }

  /**
   * Checked 예외는 예외를 잡아서 처리하거나, 던지거나  둘 중 하나는 반드시 처리해야한다.
   */

  static class Service {

    Repository repository = new Repository();

    /**
     * 예외를 잡아서 처리하는 로직
     */
    public void callCatch() {
      try {
        repository.call();
      } catch (MyCheckedException e) {
        // 예외 처리 로직
        log.info("예외 처리, message={}", e.getMessage(), e);
      }
    }

    /**
     * 체크 예외를 밖으로 던지는 코드
     * 체크 예외를 잡지안ㄹ고 밖으로 던지려면 throws 예뢰를 메서드에 필수로 선언해야 합니다.
     * @throws MyCheckedException
     */

    public void callThrow() throws MyCheckedException {
      repository.call();
    }
  }

  static class Repository {
    public void call() throws MyCheckedException {
      throw new MyCheckedException("ex");
    }

  }

}
