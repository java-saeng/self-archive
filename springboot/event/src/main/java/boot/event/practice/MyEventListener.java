package boot.event.practice;

import boot.event.ExternalClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MyEventListener {

  private final NotificationRepository notificationRepository;
  private final ExternalClient externalClient;

  @EventListener
  @Transactional
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
