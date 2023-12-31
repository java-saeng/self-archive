package boot.event.practice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long redirectId;
  private boolean isNotificationSent;

  public Notification(final Long redirectId) {
    this.redirectId = redirectId;
    isNotificationSent = false;
  }

  public void send() {
    isNotificationSent = true;
  }
}
