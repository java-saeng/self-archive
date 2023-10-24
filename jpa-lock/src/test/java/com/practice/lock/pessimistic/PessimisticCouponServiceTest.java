package com.practice.lock.pessimistic;

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
import org.springframework.dao.PessimisticLockingFailureException;

@SpringBootTest
class PessimisticCouponServiceTest {

  @Autowired
  private PessimisticCouponService pessimisticCouponService;

  @BeforeEach
  void init() {
    pessimisticCouponService.save();
  }

  @Test
  @DisplayName("비관적 락을 제대로 얻을 경우에는 아무런 실패 없이 5개의 쿠폰이 제대로 발급될 수 있다.")
  void test_pessimisticLock_success() throws Exception {

    final int executeNumber = 20;

    final ExecutorService executorService = Executors.newFixedThreadPool(10);
    final CountDownLatch countDownLatch = new CountDownLatch(executeNumber);

    final AtomicInteger successCount = new AtomicInteger();
    final AtomicInteger pessimisticLockCount = new AtomicInteger();
    final AtomicInteger insufficientQuantityCount = new AtomicInteger();

    for (int i = 0; i < executeNumber; i++) {
      executorService.execute(() -> {
        try {
          pessimisticCouponService.issueCoupon(1L);
          successCount.getAndIncrement();
          System.out.println("성공");
        } catch (PessimisticLockingFailureException iae) {
          System.out.println("비관적 락 획득 실패");
          pessimisticLockCount.getAndIncrement();
        } catch (Exception e) {
          System.out.println(e.getMessage());
          insufficientQuantityCount.getAndIncrement();
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();

    System.out.println("성공한 횟수 = " + successCount.get());
    System.out.println("비관적 락 획득 실패 횟수 = " + pessimisticLockCount.get());
    System.out.println("갯수 부족 횟수 = " + insufficientQuantityCount.get());

    final int totalCount =
        insufficientQuantityCount.get() + pessimisticLockCount.get() + successCount.get();

    assertEquals(totalCount, executeNumber);
  }

  @Test
  @DisplayName("비관적 락을 제대로 얻지 못할 경우에는 PessimisticLockingFailureException이 발생할 수 있다.")
  void test_pessimisticLock_fail() throws InterruptedException {

    final int executeNumber = 20;

    final ExecutorService executorService = Executors.newFixedThreadPool(10);

    final CountDownLatch countDownLatch = new CountDownLatch(executeNumber);

    final AtomicInteger successCount = new AtomicInteger();
    final AtomicInteger pessimisticLockCount = new AtomicInteger();
    final AtomicInteger insufficientQuantityCount = new AtomicInteger();

    for (int i = 0; i < executeNumber; i++) {
      executorService.execute(() -> {
        try {
          pessimisticCouponService.issueCouponLong(1L);
          successCount.getAndIncrement();
          System.out.println("성공");
        } catch (PessimisticLockingFailureException iae) {
          System.out.println("비관적 락 획득 실패");
          pessimisticLockCount.getAndIncrement();
        } catch (Exception e) {
          System.out.println(e.getMessage());
          insufficientQuantityCount.getAndIncrement();
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();

    System.out.println("성공한 횟수 = " + successCount.get());
    System.out.println("비관적 락 실패 횟수 = " + pessimisticLockCount.get());
    System.out.println("갯수 부족 횟수 = " + insufficientQuantityCount.get());

    final int totalCount =
        insufficientQuantityCount.get() + pessimisticLockCount.get() + successCount.get();

    assertEquals(totalCount, executeNumber);
  }
}
