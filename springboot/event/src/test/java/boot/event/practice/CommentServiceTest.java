package boot.event.practice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentServiceTest {

  @Autowired
  private CommentService commentService;
  @Autowired
  private NotificationRepository notificationRepository;

  @Test
  @DisplayName("댓글을 성공적으로 저장한다면 알림이 발송된다.")
  void test_save() throws Exception {
    final Comment comment = commentService.save("댓글");

    final Notification notification = notificationRepository.findByRedirectId(comment.getId());

    assertTrue(notification.isNotificationSent());
  }
}
