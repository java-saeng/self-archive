package com.practice.lock.optimistic;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptimisticCouponRepository extends JpaRepository<OptimisticCoupon, Long> {

  @Override
//  @Lock(LockModeType.OPTIMISTIC) 해당 옵션이 있으면 조회할 때도 version 확인을 함
  Optional<OptimisticCoupon> findById(Long aLong);
}
