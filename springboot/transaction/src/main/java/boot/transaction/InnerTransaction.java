package boot.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class InnerTransaction {

  public void checkedException() throws CheckedException {
    log.info("내부 트랜잭션 checkedException");
    throw new CheckedException();
  }

  public void unCheckedException() {
    log.info("내부 트랜잭션 unCheckedException");
    throw new UnCheckedException();
  }

  public void success() {
    log.info("내부 트랜잭션 시작");
    log.info("내부 트랜잭션 커밋");
  }
}
