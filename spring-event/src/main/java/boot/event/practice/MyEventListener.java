package boot.event.practice;

import boot.event.ExternalClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyEventListener {

  private final NotificationRepository notificationRepository;
  private final ExternalClient externalClient;

//  @TransactionalEventListener
//  @Transactional(propagation = Propagation.REQUIRES_NEW)
//  public void listen(final Notification notification) {
//
//    final Notification savedNotification = notificationRepository.save(notification);
//
//    externalClient.sendMessage();
//
//    savedNotification.send();
//  }

  //  @Async
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void listen(final Notification notification) {

    final Notification savedNotification = notificationRepository.save(notification);

    try {
      externalClient.sendMessageFailException();
    } catch (Exception e) {
      log.error("[파이어베이스 에러 메시지] = {}", e.getMessage(), e);
      return;
    }

    savedNotification.send();
  }
}
