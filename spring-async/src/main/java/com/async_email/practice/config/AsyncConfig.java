package com.async_email.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4); //유휴 상태일 경우에도 풀에 유지할 스레드 수
        executor.setMaxPoolSize(4); //스레드풀에서 최대로 사용 가능한 스레드 개수 지정
        executor.setQueueCapacity(100); //스레드 풀에 의해 실행되기 전, 작업을 보관하는데 사용되는 대기열의 용량
        executor.setThreadNamePrefix("java-saeng"); //스레드 풀에서 생성된 스레드의 이름 prefix
        executor.initialize(); //스레드 풀을 초기화하기 위해 호출
        return executor;
    }
}
