package boot.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OuterTransaction {

  private final InnerTransaction innerTransaction;

  public void innerCheckedException() throws CheckedException {
    log.info("외부 트랜잭션 시작");

    innerTransaction.checkedException();

    log.info("외부 트랜잭션 커밋");
  }

  public void innerUnCheckedException() {
    log.info("외부 트랜잭션 시작");

    innerTransaction.unCheckedException();

    log.info("외부 트랜잭션 커밋");
  }

  public void outerCheckedException() throws CheckedException {
    log.info("외부 트랜잭션 시작");

    innerTransaction.success();

    log.info("외부 트랜잭션 checkedException");
    throw new CheckedException();
  }

  public void outerUnCheckedException() {
    log.info("외부 트랜잭션 시작");

    innerTransaction.success();

    log.info("외부 트랜잭션 unCheckedException");
    throw new UnCheckedException();
  }
}
