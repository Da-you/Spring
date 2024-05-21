package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogin1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogin2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;

@Slf4j
public class ContextV1Test {


  @Test
  void strategyV0() {
    logic1();
    logic2();
  }

  private void logic1() {
    long startTime = System.currentTimeMillis();
    log.info("비즈니스 로직1 실행");
    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("resultTime ={}", resultTime);
  }

  private void logic2() {
    long startTime = System.currentTimeMillis();
    log.info("비즈니스 로직2 실행");
    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("resultTime ={}", resultTime);
  }

  /**
   * 전략 패턴 사용
   */

  @Test
  void strategyV1() {
    StrategyLogin1 strategyLogin1 = new StrategyLogin1();
    ContextV1 contextV1 = new ContextV1(strategyLogin1);
    contextV1.execute();

    StrategyLogin2 strategyLogin2 = new StrategyLogin2();
    ContextV1 contextV2 = new ContextV1(strategyLogin2);
    contextV2.execute();
  }

  /**
   * 익명 클래스 사용
   */
  @Test
  void strategyV2() {
    Strategy strategy1 = new Strategy() {

      @Override
      public void call() {
        log.info("비즈니스 로직1 실행");
      }
    };
    ContextV1 contextV1 = new ContextV1(strategy1);
    contextV1.execute();

    Strategy strategy2 = new Strategy() {

      @Override
      public void call() {
        log.info("비즈니스 로직1 실행");
      }
    };
    ContextV1 contextV2 = new ContextV1(strategy2);
    contextV2.execute();
  }

  @Test
  void strategyV3() {
    ContextV1 contextV1 = new ContextV1(new Strategy() {
      @Override
      public void call() {
        log.info("비즈니스 로직1 실행");
      }
    });
    contextV1.execute();

    ContextV1 contextV2 = new ContextV1(new Strategy() {
      @Override
      public void call() {
        log.info("비즈니스 로직2 실행");
      }
    });
    contextV2.execute();
  }


  @Test
  void strategyV4() {
    ContextV1 contextV1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
    contextV1.execute();

    ContextV1 contextV2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
    contextV2.execute();
  }

}
