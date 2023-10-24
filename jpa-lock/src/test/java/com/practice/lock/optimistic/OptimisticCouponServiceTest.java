package com.practice.lock.optimistic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@SpringBootTest
class OptimisticCouponServiceTest {

  @Autowired
  private OptimisticCouponService optimisticCouponService;

  @BeforeEach
  void init() {
    optimisticCouponService.save();
  }

  @Test
  @DisplayName("낙관적 락을 사용하여 쿠폰을 발급받으면 최초 요청자에게만 쿠폰이 발급될 수 있다.")
  void test_optimisticLock() throws InterruptedException {

    final int executeNumber = 20;

    final ExecutorService executorService = Executors.newFixedThreadPool(10);
    final CountDownLatch countDownLatch = new CountDownLatch(executeNumber);

    final AtomicInteger successCount = new AtomicInteger();
    final AtomicInteger optimisticLockCount = new AtomicInteger();
    final AtomicInteger insufficientQuantityCount = new AtomicInteger();

    for (int i = 0; i < executeNumber; i++) {
      executorService.execute(() -> {
        try {
          optimisticCouponService.issueCoupon(1L);
          successCount.getAndIncrement();
          System.out.println("성공");
        } catch (ObjectOptimisticLockingFailureException oolfe) {
          System.out.println("낙관적 락 획득 실패");
          optimisticLockCount.getAndIncrement();
        } catch (Exception e) {
          insufficientQuantityCount.getAndIncrement();
          System.out.println("e.getMessage() = " + e.getMessage());
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();

    System.out.println("성공한 횟수 = " + successCount.get());
    System.out.println("낙관적 락 획득 실패 횟수 = " + optimisticLockCount.get());
    System.out.println("갯수 부족 횟수 = " + insufficientQuantityCount.get());

    final int totalCount =
        insufficientQuantityCount.get() + optimisticLockCount.get() + successCount.get();

    assertEquals(totalCount, executeNumber);
  }
}
