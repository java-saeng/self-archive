package boot.event.practice;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

  private final ApplicationEventPublisher publisher;

  public void publish(final Comment comment) {
    final Notification notification = new Notification(comment.getId());

    publisher.publishEvent(notification);
  }
}
