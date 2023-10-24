package com.async_email.practice.domain.email.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailAuthServiceTest {

    @Autowired
    EmailAuthService emailAuthService;

    @Test
    void testAsync() {
        for (int i = 0; i < 20; i++) {
            emailAuthService.asyncTest();
        }
    }
}