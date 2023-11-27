package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnCheckedTest {

  /**
   * RuntimeException을 상속받은 예외는 언체크 예외가 된다
   */
  static class MyUnCheckedException extends RuntimeException{
    public MyUnCheckedException(String message) {
      super(message);
    }
  }

  /**
   * UnChecked 예외는
   * 예외를 잡거나, 던지지 않아도 된다.
   * 예외를 잡지안으면 자동으로 밖으로 던진다
   */
  static class  Service {
    Repository repository = new Repository();

    /**
     * 팔요랑 경우 예외를 잡아서 처리해도 된다
     */
    @Test
    public void unChecked_Catch(){
      try {
        repository.call();
      }catch (MyUnCheckedException e){
        log.info("예외 처리, message={}",e.getMessage(),MyUnCheckedException.class);
      }
    }
    @Test
    void unChecked_throw(){
      Service service = new Service();
      Assertions.assertThatThrownBy(()-> service.callThrow() )
          .isInstanceOf(MyUnCheckedException.class); 
    }

    /**
     * 예외를 잡지 않아도 된다. 자연스럽게 상위로 넘어감
     * 체크 예외와 다르게 throw를 선언하지 않아도 됨
     */
    public void callThrow(){
      repository.call();
}
  }
static class Repository{
    public void call(){
      throw new MyUnCheckedException("ex");
    }
}

}
