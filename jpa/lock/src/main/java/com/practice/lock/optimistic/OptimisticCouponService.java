package com.practice.lock.optimistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticCouponService {

  private final OptimisticCouponRepository optimisticCouponRepository;

  @Transactional
  public void issueCoupon(long couponId) {
    final OptimisticCoupon optimisticCoupon = optimisticCouponRepository.findById(couponId).get();

    optimisticCoupon.issue();
  }

  public void save() {
    optimisticCouponRepository.save(new OptimisticCoupon(5));
  }
}
