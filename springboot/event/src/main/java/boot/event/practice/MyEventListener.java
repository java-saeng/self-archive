package boot.event.practice;

import boot.event.ExternalClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MyEventListener {

  private final NotificationRepository notificationRepository;
  private final ExternalClient externalClient;

  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void listen(final Notification notification) {

    final Notification savedNotification = notificationRepository.save(notification);

    try {
      externalClient.sendMessage();
    } catch (final Exception exception) {
      throw new IllegalArgumentException("알림 정상적으로 발송 X");
    }

    savedNotification.send();
  }
}
