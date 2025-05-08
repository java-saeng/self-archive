package com.practice.self_archive.async;

import com.practice.self_archive.SelfArchiveApplication;
import java.util.Map;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class AsyncTestService {

    private static final Logger log = LoggerFactory.getLogger(AsyncTestService.class);

    private final ApplicationContext applicationContext;

    public AsyncTestService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 기본 @Async (어떤 Executor가 사용되는지 테스트)
     */
    @Async
    public void asyncTest(int i) {
        try {
            Thread.sleep(500L); // 스레드 실행을 명확히 확인하기 위해 지연 추가

            log.info("기본 비동기 메소드 | 실행 스레드: {} | 작업 번호: {}",
                Thread.currentThread().getName(), i);

//            checkExecutorImplementations();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 명시적으로 Executor 이름을 지정한 @Async
    @Async("customTaskExecutor")
    public void asyncTestWithNamedExecutor(int i) {
        try {
            Thread.sleep(500L);

            log.info("Custom Executor 비동기 메소드 | 실행 스레드: {} | 작업 번호: {}",
                Thread.currentThread().getName(), i);

//            checkExecutorImplementations();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 다른 Executor 이름을 지정한 @Async
    @Async("anotherTaskExecutor")
    public void asyncTestWithAnotherNamedExecutor(int i) {
        try {
            Thread.sleep(500L);

            log.info("Another Executor 비동기 메소드 | 실행 스레드: {} | 작업 번호: {}",
                Thread.currentThread().getName(), i);

//            checkExecutorImplementations();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkExecutorImplementations() {
        // ApplicationContext에 등록된 모든 Executor 타입의 빈을 조회
        Map<String, Executor> executors = applicationContext.getBeansOfType(Executor.class);

        log.info("등록된 Executor 빈 목록 및 구현체:");
        executors.forEach((name, executor) -> {
            log.info("빈 이름: {}", name);
            log.info("구현체 클래스: {}", executor.getClass());

            // ThreadPoolTaskExecutor인 경우 추가 정보 출력
            if (executor instanceof ThreadPoolTaskExecutor) {
                ThreadPoolTaskExecutor tpExecutor = (ThreadPoolTaskExecutor) executor;
                log.info("  - 코어 풀 크기: {}", tpExecutor.getCorePoolSize());
                log.info("  - 최대 풀 크기: {}", tpExecutor.getMaxPoolSize());
                log.info("  - 대기 큐 용량: {}", tpExecutor.getQueueCapacity());
                log.info("  - 스레드 이름 접두사: {}", tpExecutor.getThreadNamePrefix());
            }
        });
    }
}