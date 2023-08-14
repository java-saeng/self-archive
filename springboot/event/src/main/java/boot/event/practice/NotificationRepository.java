package boot.event.practice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  Notification findByRedirectId(final Long redirectId);
}
