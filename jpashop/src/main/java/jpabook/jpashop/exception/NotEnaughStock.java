package jpabook.jpashop.exception;

public class NotEnaughStock extends RuntimeException {

  public NotEnaughStock() {
    super();
  }

  public NotEnaughStock(String message) {
    super(message);
  }

  public NotEnaughStock(String message, Throwable cause) {
    super(message, cause);
  }

  public NotEnaughStock(Throwable cause) {
    super(cause);
  }

  protected NotEnaughStock(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
