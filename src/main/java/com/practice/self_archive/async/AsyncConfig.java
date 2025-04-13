package com.practice.self_archive.async;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    // 테스트 1: 주석 처리된 상태로 시작하여 Executor 빈이 없는 경우 테스트

    // 테스트 2: 단일 Executor 빈을 위해 아래 메소드의 주석을 해제
//    @Bean("taskExecutor") // 기본 이름 사용
//    public Executor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5);
//        executor.setMaxPoolSize(10);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("DefaultExecutor-");
//        executor.initialize();
//        return executor;
//    }
//
//    // 테스트 3: 여러 Executor 빈이 있는 경우를 위해 아래 메소드들의 주석을 해제
    @Bean("customTaskExecutor")
    public Executor customTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("CustomExecutor-");
        executor.initialize();
        return executor;
    }
//
//    @Bean("anotherTaskExecutor")
//    public Executor anotherTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(10);
//        executor.setMaxPoolSize(20);
//        executor.setQueueCapacity(200);
//        executor.setThreadNamePrefix("AnotherExecutor-");
//        executor.initialize();
//        return executor;
//    }
}