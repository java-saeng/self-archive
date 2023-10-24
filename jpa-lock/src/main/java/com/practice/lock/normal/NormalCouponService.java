package com.practice.lock.normal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NormalCouponService {

  private final NormalCouponRepository normalCouponRepository;

  @Transactional
  public void issueCoupon(long couponId) {
    final NormalCoupon normalCoupon = normalCouponRepository.findById(couponId).get();

    normalCoupon.issue();
  }

  public void save() {
    normalCouponRepository.save(new NormalCoupon(5));
  }
}
