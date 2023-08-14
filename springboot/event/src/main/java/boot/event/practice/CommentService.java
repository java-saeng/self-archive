package boot.event.practice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

  private final CommentRepository commentRepository;
  private final EventPublisher eventPublisher;

  @Transactional
  public Comment save(final String content) {
    final Comment comment = new Comment(content);

    final Comment savedComment = commentRepository.save(comment);

    eventPublisher.publish(comment);

    return savedComment;
  }
}
