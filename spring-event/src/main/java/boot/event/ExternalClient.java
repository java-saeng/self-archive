package boot.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExternalClient {

  public void sendMessage() {
    log.info("파이어베이스에 메시지 요청 성공적");
  }

  public void sendMessageFailException() throws Exception {
    log.info("파이어베이스 서버 문제로 메시지 요청 실패");
    throw new Exception("파이어베이스 내부 문제");
  }

  public void sendMessageFailRuntimeException()  {
    log.info("파이어베이스 서버 문제로 메시지 요청 실패");
    throw new IllegalArgumentException("파이어베이스 문제");
  }
}
