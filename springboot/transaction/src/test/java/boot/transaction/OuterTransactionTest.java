package boot.transaction;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OuterTransactionTest {

  @Autowired
  OuterTransaction outerTransaction;

  @Test
  @DisplayName("외부 트랜잭션 커밋, 내부 트랜잭션 checkedException")
  void inner_checkedException() throws Exception {
    assertThatThrownBy(() -> outerTransaction.innerCheckedException())
        .isInstanceOf(CheckedException.class);
  }

  @Test
  @DisplayName("외부 트랜잭션 커밋, 내부 트랜잭션 unCheckedException")
  void inner_unCheckedException() throws Exception {
    assertThatThrownBy(() -> outerTransaction.innerUnCheckedException())
        .isInstanceOf(UnCheckedException.class);
  }

  @Test
  @DisplayName("외부 트랜잭션 checkedException, 내부 트랜잭션 커밋")
  void outer_checkedException() throws Exception {
    assertThatThrownBy(() -> outerTransaction.outerCheckedException())
        .isInstanceOf(CheckedException.class);

  }

  @Test
  @DisplayName("외부 트랜잭션 unCheckedException, 내부 트랜잭션 커밋")
  void outer_unCheckedException() throws Exception {
    assertThatThrownBy(() -> outerTransaction.outerUnCheckedException())
        .isInstanceOf(UnCheckedException.class);
  }
}
