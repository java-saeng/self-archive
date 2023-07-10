package com.practice.lock.normal;

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

@SpringBootTest
class NormalCouponServiceTest {

  @Autowired
  NormalCouponService normalCouponService;

  @BeforeEach
  void init() {
    normalCouponService.save();
  }

  @Test
  @DisplayName("동시에 쿠폰을 발급하게 되면 존재한 쿠폰의 개수 이상으로 쿠폰이 발급될 수 있다.")
  void test_not_lock() throws InterruptedException {

    final int executeNumber = 20;

    final ExecutorService executorService = Executors.newFixedThreadPool(10);
    final CountDownLatch countDownLatch = new CountDownLatch(executeNumber);

    final AtomicInteger successCount = new AtomicInteger();
    final AtomicInteger failCount = new AtomicInteger();

    for (int i = 0; i < executeNumber; i++) {
      executorService.execute(() -> {
        try {
          normalCouponService.issueCoupon(1L);
          successCount.getAndIncrement();
          System.out.println("쿠폰 발급");
        } catch (Exception e) {
          failCount.getAndIncrement();
          System.out.println(e.getMessage());
        }
        countDownLatch.countDown();
      });
    }

    countDownLatch.await();

    System.out.println("발급된 쿠폰의 개수 = " + successCount.get());
    System.out.println("실패한 횟수 = " + failCount.get());

    assertEquals(failCount.get() + successCount.get(), executeNumber);
  }
}
