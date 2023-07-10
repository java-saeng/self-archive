package com.practice.lock.pessimistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PessimisticCouponService {

  private final PessimisticCouponRepository pessimisticCouponRepository;

  @Transactional
  public void issueCoupon(long couponId) {
    final PessimisticCoupon pessimisticCoupon = pessimisticCouponRepository.findById(couponId)
        .get();

    pessimisticCoupon.issue();
  }

  @Transactional
  public void issueCouponLong(long couponId) {
    final PessimisticCoupon pessimisticCoupon = pessimisticCouponRepository.findById(couponId)
        .get();

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
    }

    pessimisticCoupon.issue();
  }

  public void save() {
    pessimisticCouponRepository.save(new PessimisticCoupon(5));
  }
}
