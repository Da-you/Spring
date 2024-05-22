package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 전략을 파라미터로 전달
 */
@Slf4j
public class ContextV2 {
    private Strategy strategy;


  public void execute(Strategy strategy) {
    long startTime = System.currentTimeMillis();
    strategy.call();
    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("resultTime ={}", resultTime);
  }
}
