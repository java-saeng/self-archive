package com.practice.lock.pessimistic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class PessimisticCoupon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int count;

  public PessimisticCoupon(final int count) {
    this.count = count;
  }

  public void issue() {
    if (count <= 0) {
      throw new IllegalArgumentException("수량 부족");
    }
    count -= 1;
  }
}
