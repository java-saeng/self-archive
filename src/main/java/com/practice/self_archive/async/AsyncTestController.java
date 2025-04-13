package com.practice.self_archive.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AsyncTestController {

    private final AsyncTestService asyncTestService;

    @GetMapping("/async-default")
    public String asyncDefault(@RequestParam(value = "count", defaultValue = "5") int count) {
        log.info("컨트롤러 스레드: {} | 테스트: 기본 Executor", Thread.currentThread().getName());

        for (int i = 1; i <= count; i++) {
            asyncTestService.asyncTest(i);
        }

        return "기본 @Async 테스트 완료.";
    }

    @GetMapping("/async-custom")
    public String asyncCustom(@RequestParam(value = "count", defaultValue = "5") int count) {
        log.info("컨트롤러 스레드: {} | 테스트: 커스텀 Executor", Thread.currentThread().getName());

        for (int i = 1; i <= count; i++) {
            asyncTestService.asyncTestWithNamedExecutor(i);
        }

        return "커스텀 @Async(\"customTaskExecutor\") 테스트 완료.";
    }

    @GetMapping("/async-another")
    public String asyncAnother(@RequestParam(value = "count", defaultValue = "5") int count) {
        log.info("컨트롤러 스레드: {} | 테스트: 다른 커스텀 Executor", Thread.currentThread().getName());

        for (int i = 1; i <= count; i++) {
            asyncTestService.asyncTestWithAnotherNamedExecutor(i);
        }

        return "다른 커스텀 @Async(\"anotherTaskExecutor\") 테스트 완료";
    }

    @GetMapping("/async-all")
    public String asyncAll(@RequestParam(value = "count", defaultValue = "3") int count) {
        log.info("컨트롤러 스레드: {} | 테스트: 모든 Executor", Thread.currentThread().getName());

        for (int i = 1; i <= count; i++) {
            asyncTestService.asyncTest(i);
            asyncTestService.asyncTestWithNamedExecutor(i);
            asyncTestService.asyncTestWithAnotherNamedExecutor(i);
        }

        return "모든 @Async 메소드 테스트 완료";
    }
}