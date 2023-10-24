package com.practice.lock.pessimistic;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface PessimisticCouponRepository extends JpaRepository<PessimisticCoupon, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<PessimisticCoupon> findById(Long id);
}
