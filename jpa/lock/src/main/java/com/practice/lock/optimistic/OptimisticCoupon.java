package com.practice.lock.optimistic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class OptimisticCoupon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int count;

  @Version
  private Integer version;

  public OptimisticCoupon(final int count) {
    this.count = count;
  }

  public void issue() {
    if (count <= 0) {
      throw new IllegalArgumentException("수량 부족");
    }
    count -= 1;
  }
}
